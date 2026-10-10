package com.example.polarh10activityviewer.session

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
internal fun CadenceDetailsDialog(onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Cadence") }, text = {
        Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("How many steps you take per minute.", style = MaterialTheme.typography.bodyMedium)
            StepPeakIllustration()
            Text("Each rise above the threshold and return to the baseline produces a step candidate. Four consecutive accepted candidates confirm walking, including those first four steps.")
            Text("Cadence = confirmed steps ÷ window seconds × 60.\nUses the latest 5 seconds, or the available shorter window after warm-up. No confirmed step for 2 seconds gives 0.")
            Text("Mean and Max on the card use valid readings from the whole session, including recorded zeros.")
        }
    }, confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } })
}

@Composable
private fun StepPeakIllustration() {
    val signal = sessionBlue()
    val reference = MaterialTheme.colorScheme.onSurfaceVariant
    val axis = MaterialTheme.colorScheme.outline
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Step peaks · illustration", style = MaterialTheme.typography.titleSmall)
        Text("Filtered acceleration", style = MaterialTheme.typography.bodySmall)
        Canvas(Modifier.fillMaxWidth().height(100.dp).semantics {
            contentDescription = "Illustration, not live data: four acceleration peaks rise above the threshold and return to the baseline. Dots mark step candidates."
        }) {
            val left = 6.dp.toPx()
            val width = size.width - 2 * left
            val baseline = size.height * 0.78f
            val threshold = size.height * 0.43f
            val peak = size.height * 0.15f
            drawLine(axis, Offset(left, size.height * 0.92f), Offset(left + width, size.height * 0.92f))
            for (level in listOf(baseline, threshold)) {
                drawLine(reference.copy(alpha = 0.55f), Offset(left, level), Offset(left + width, level),
                    1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 4.dp.toPx())))
            }
            val path = Path().apply {
                moveTo(left, baseline)
                repeat(4) { index ->
                    val x = left + width * index / 4
                    val cycle = width / 4
                    cubicTo(x + cycle * 0.2f, baseline, x + cycle * 0.25f, peak, x + cycle * 0.45f, peak)
                    cubicTo(x + cycle * 0.65f, peak, x + cycle * 0.65f, baseline + 5.dp.toPx(), x + cycle * 0.8f, baseline + 5.dp.toPx())
                    cubicTo(x + cycle * 0.9f, baseline + 5.dp.toPx(), x + cycle * 0.95f, baseline, x + cycle, baseline)
                }
            }
            drawPath(path, signal, style = Stroke(2.dp.toPx()))
            repeat(4) { index ->
                drawCircle(signal, 3.dp.toPx(), Offset(left + width * (index + 0.45f) / 4, peak))
            }
        }
        Text("Upper dashed line: threshold\nLower dashed line: baseline · Dots: step candidates",
            style = MaterialTheme.typography.bodySmall)
        Text("Time → · Example only, not live data", style = MaterialTheme.typography.bodySmall)
    }
}
