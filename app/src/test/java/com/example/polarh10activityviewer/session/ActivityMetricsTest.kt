package com.example.polarh10activityviewer.session

import org.junit.Assert.*
import org.junit.Test
import com.polar.sdk.api.PolarBleApi.PolarDeviceDataType

class ActivityMetricsTest {
    @Test fun tenPointPresentationKeepsRawMetricsAndDistinguishesMissingFromZero() {
        val stored = ActivityMetrics(intensity = 17.0 / 6.0, cardioLoad = 85.0)
        assertEquals(6, ActivityMetricsCalculator.intensityRating(stored.intensity))
        assertEquals(5, ActivityMetricsCalculator.cardioLoadRating(stored.cardioLoad))
        assertEquals(85.0, stored.cardioLoad!!, 0.0)
        assertEquals(2, ActivityMetricsCalculator.intensityRating(1.0))
        assertEquals(10, ActivityMetricsCalculator.intensityRating(5.0))
        assertEquals(9, ActivityMetricsCalculator.cardioLoadRating(900.0))
        assertEquals(10, ActivityMetricsCalculator.cardioLoadRating(1900.0))
        assertEquals(0, ActivityMetricsCalculator.cardioLoadRating(0.0))
        assertNull(ActivityMetricsCalculator.intensityRating(null))
        assertNull(ActivityMetricsCalculator.cardioLoadRating(null))
    }

    @Test fun loadScoreMatchesReferenceExamplesWithoutChangingRawLoad() {
        for ((raw, score) in listOf(25.0 to 20.0, 100.0 to 50.0, 300.0 to 75.0, 900.0 to 90.0)) {
            val metrics = ActivityMetrics(sessionStrain = raw, sessionStrainScore = ActivityMetricsCalculator.strainScore(raw))
            assertEquals(score, metrics.sessionStrainScore!!, 1e-10)
            assertEquals(raw, metrics.sessionStrain!!, 0.0)
        }
        assertEquals(1.768172888015717, ActivityMetricsCalculator.strainScore(1.8)!!, 1e-10)
    }

    @Test fun loadScorePreservesMissingZeroAndOrderingWithoutEarlySaturation() {
        assertNull(ActivityMetrics().sessionStrainScore)
        assertEquals(0.0, ActivityMetricsCalculator.strainScore(0.0)!!, 0.0)
        val scores = listOf(1.8, 25.0, 100.0, 300.0, 900.0, 6000.0, 1_000_000.0)
            .map { ActivityMetricsCalculator.strainScore(it)!! }
        assertTrue(scores.all { it > 0 && it < 100 })
        assertTrue(scores.zipWithNext().all { (a, b) -> b > a })
    }

    private fun snapshot(values: List<Double?> = List(30) { listOf(100.0, 110.0, 120.0)[it % 3] }): SessionSnapshot {
        val record = SessionRecord("metrics", 0, null, startedAt = 1, endedAt = 1_800_001,
            durationMs = 1_800_000, summary = SessionSummary(validHrCount = 30,
                zoneDurationsMs = listOf(0, 600_000, 900_000, 300_000, 0), meanCadence = 999.0))
        return SessionSnapshot(record, emptyList(), values.mapIndexed { index, value ->
            MotionHistoryPoint(record.id, index.toLong(), index * 1000L, value, index % 4 == 0)
        })
    }

    @Test fun documentedZoneExampleUsesMillisecondsAndExcludesUnclassifiedTime() {
        val s = snapshot()
        val a = ActivityMetricsCalculator.calculate(s)
        assertEquals(85.0/30, a.intensity!!, 1e-12)
        assertEquals(85.0, a.cardioLoad!!, 1e-12)
        assertEquals(a, ActivityMetricsCalculator.calculate(s.copy(record = s.record.copy(
            summary = s.record.summary.copy(unclassifiedMs = 5000)))))
    }

    @Test fun cvUsesPopulationVarianceAndSelectedMeanNotSummaryMean() {
        val expected = 7.422696190252054
        assertEquals(expected, ActivityMetricsCalculator.populationCv(listOf(100.0,110.0,120.0))!!, 1e-10)
        assertEquals(expected, ActivityMetricsCalculator.calculate(snapshot()).cadenceCvPercent!!, 1e-10)
    }

    @Test fun validPointThresholdIsThirtyAndConstantCadenceIsMeasuredZero() {
        assertNull(ActivityMetricsCalculator.calculate(snapshot(List(29) { 110.0 })).cadenceCvPercent)
        val result = ActivityMetricsCalculator.calculate(snapshot(List(30) { 110.0 }))
        assertEquals(30, result.cadencePointCount)
        assertEquals(0.0, result.cadenceCvPercent!!, 0.0)
    }

    @Test fun excludesMissingStoppedNonfiniteAndNegativeCadenceWithoutPadding() {
        val values = List(29) { 110.0 } + listOf(null, 0.0, Double.NaN, Double.POSITIVE_INFINITY, -2.0)
        val result = ActivityMetricsCalculator.calculate(snapshot(values))
        assertEquals(29, result.cadencePointCount)
        assertNull(result.cadenceCvPercent)
    }

    @Test fun finalBucketValueWinsEvenWhenMissingAndBoundaryDoesNotExcludeValidPoint() {
        val s = snapshot()
        val duplicate = s.motionPoints.last().copy(cadence = null)
        assertEquals(29, ActivityMetricsCalculator.calculate(s.copy(motionPoints = s.motionPoints + duplicate)).cadencePointCount)
        assertEquals(30, ActivityMetricsCalculator.calculate(s).cadencePointCount)
    }

    @Test fun ignoresOtherSessionsAndPointsOutsideFinalTimeBoundary() {
        val s = snapshot(List(29) { 110.0 })
        val p = s.motionPoints.last().copy(secondBucket = 5000)
        val result = ActivityMetricsCalculator.calculate(s.copy(motionPoints = s.motionPoints + listOf(
            p.copy(sessionId = "other"), p.copy(elapsedMs = -1), p.copy(elapsedMs = 1_800_001))))
        assertEquals(29, result.cadencePointCount)
    }

    @Test fun zeroZoneDurationIsUnknownAndFiveZoneBoundariesRetainFractionalMinutes() {
        val s = snapshot()
        for (zone in 0..4) {
            val summary = s.record.summary.copy(zoneDurationsMs = List(5) { if (it == zone) 500L else 0L })
            val r = ActivityMetricsCalculator.calculate(s.copy(record = s.record.copy(summary = summary)))
            assertEquals((zone+1).toDouble(), r.intensity!!, 0.0)
            assertEquals((zone+1)/120.0, r.cardioLoad!!, 1e-12)
        }
        val r = ActivityMetricsCalculator.calculate(s.copy(record = s.record.copy(summary = SessionSummary())))
        assertNull(r.intensity); assertNull(r.cardioLoad)
    }

    @Test fun archivedSessionsHaveNoMetricsWhileOrdinaryPartialStreamsCanHaveMetrics() {
        val s = snapshot()
        assertEquals(ActivityMetrics(), ActivityMetricsCalculator.calculate(s.copy(record = s.record.copy(collectionIncomplete = true))))
        assertNotNull(ActivityMetricsCalculator.calculate(s.copy(record = s.record.copy(interrupted = true))).intensity)
    }

    @Test fun cvIsNotClampedToOneHundred() {
        assertTrue(ActivityMetricsCalculator.populationCv(List(29) { 1.0 } + 1000.0)!! > 100)
    }

    private fun strainRecord(cadence: Double? = 120.0, duration: Long = 1_800_000): SessionRecord {
        val r = snapshot().record
        return r.copy(durationMs = duration,
            summary = r.summary.copy(meanCadence = cadence, totalSteps = 3600,
                zoneDurationsMs = listOf(0, 0, duration, 0, 0)),
            streams = r.streams + (PolarDeviceDataType.ACC to StreamObservation(received = true)))
    }

    @Test fun strainCombinesHeartCadenceAndActiveTimeUsingDocumentedExamples() {
        assertEquals(245.25, ActivityMetricsCalculator.strain(strainRecord(90.0))!!, 1e-10)
        assertEquals(289.0, ActivityMetricsCalculator.strain(strainRecord())!!, 1e-10)
        assertEquals(578.0, ActivityMetricsCalculator.strain(strainRecord(duration = 3_600_000))!!, 1e-10)
    }

    @Test fun strainCapsCadenceReferenceAndKeepsRecordedStationaryTime() {
        assertEquals(414.0, ActivityMetricsCalculator.strain(strainRecord(180.0))!!, 1e-10)
        assertEquals(414.0, ActivityMetricsCalculator.strain(strainRecord(240.0))!!, 1e-10)
        assertEquals(189.0, ActivityMetricsCalculator.strain(strainRecord(0.0))!!, 1e-10)
    }

    @Test fun strainRequiresUsableCadenceDurationAndClassifiedHeartTime() {
        for (cadence in listOf(null, -1.0, Double.NaN, Double.POSITIVE_INFINITY))
            assertNull(ActivityMetricsCalculator.strain(strainRecord(cadence)))
        assertNull(ActivityMetricsCalculator.strain(strainRecord(duration = 0)))
        assertNull(ActivityMetricsCalculator.strain(strainRecord(duration = -1)))
        val r = strainRecord()
        assertNull(ActivityMetricsCalculator.strain(r.copy(summary = r.summary.copy(zoneDurationsMs = List(5) { 0L }))))
    }

    @Test fun strainRejectsIncompleteAccAndArchivesButNotMissingUnrelatedStreams() {
        val r = strainRecord()
        for (acc in listOf(StreamObservation(), StreamObservation(true, missing = true), StreamObservation(true, failed = true)))
            assertNull(ActivityMetricsCalculator.strain(r.copy(streams = r.streams + (PolarDeviceDataType.ACC to acc))))
        assertNull(ActivityMetricsCalculator.strain(r.copy(collectionIncomplete = true)))
        assertEquals(289.0, ActivityMetricsCalculator.strain(r.copy(streams = r.streams +
            (PolarDeviceDataType.ECG to StreamObservation(false, failed = true))))!!, 1e-10)
    }

    @Test fun strainDoesNotExtrapolateMissingHeartTimeOrUseWallTimeAndExtraStepWeight() {
        val r = strainRecord()
        val partial = r.copy(summary = r.summary.copy(zoneDurationsMs = listOf(0,0,900_000,0,0), unclassifiedMs = 900_000))
        assertEquals(194.5, ActivityMetricsCalculator.strain(partial)!!, 1e-10)
        assertEquals(ActivityMetricsCalculator.strain(r), ActivityMetricsCalculator.strain(r.copy(
            endedAt = r.endedAt!! + 3_600_000, summary = r.summary.copy(totalSteps = 99999))))
    }

    @Test fun strainPreservesFractionalMinutesAndSquaresEachZoneNotTheMeanZone() {
        assertEquals(289.0 / 3600, ActivityMetricsCalculator.strain(strainRecord(duration = 500))!!, 1e-10)
        val r = strainRecord()
        assertEquals(373.0, ActivityMetricsCalculator.strain(r.copy(summary = r.summary.copy(
            zoneDurationsMs = listOf(900_000,0,0,0,900_000))))!!, 1e-10)
    }

    @Test fun finalizationComputesStrainWithoutInputAndRecordsVersionTwo() {
        val r = strainRecord()
        val m = snapshot().copy(record = r).withActivityMetrics().record.summary.activityMetrics
        assertEquals(2, m.algorithmVersion)
        assertEquals(289.0, m.sessionStrain!!, 1e-10)
        assertEquals(74.293059125964, m.sessionStrainScore!!, 1e-10)
    }
}
