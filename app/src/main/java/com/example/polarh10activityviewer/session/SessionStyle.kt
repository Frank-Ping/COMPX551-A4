package com.example.polarh10activityviewer.session

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Session-only presentation keeps Welcome and History styles unchanged.
internal val SessionInset = 10.dp
internal val SessionGap = 5.dp
internal val SessionCorner = 5.dp

@Composable internal fun sessionBlue() = if (MaterialTheme.colorScheme.background.luminance() > 0.5f)
    Color(0xFF0055FF) else MaterialTheme.colorScheme.primary

@Composable internal fun sessionBackground() = if (MaterialTheme.colorScheme.background.luminance() > 0.5f)
    Color(0xFFF1F8FA) else MaterialTheme.colorScheme.background

@Composable internal fun sessionBorder() = if (MaterialTheme.colorScheme.background.luminance() > 0.5f)
    Color(0xFFD5E1EF) else MaterialTheme.colorScheme.outlineVariant

@Composable internal fun SessionCard(modifier: Modifier = Modifier, title: String? = null, content: @Composable ColumnScope.() -> Unit) {
    val typography = MaterialTheme.typography.copy(
        titleMedium = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp, lineHeight = 18.sp, fontWeight = FontWeight.Bold),
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontSize = 13.sp, lineHeight = 18.sp, letterSpacing = 0.sp),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, lineHeight = 17.sp),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 16.sp)
    )
    Surface(modifier.fillMaxWidth(), shape = RoundedCornerShape(SessionCorner),
        border = BorderStroke(1.dp, sessionBorder())) {
        MaterialTheme(typography = typography) {
            Column(Modifier.padding(horizontal = SessionInset, vertical = 4.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                title?.let { Text(it, Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium) }
                content()
            }
        }
    }
}

@Composable internal fun SessionStatistic(label: String, value: String, modifier: Modifier = Modifier,
    align: TextAlign = TextAlign.Start) {
    Text(buildAnnotatedString {
        append("$label: ")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(value) }
    }, modifier, style = MaterialTheme.typography.bodySmall, textAlign = align)
}
