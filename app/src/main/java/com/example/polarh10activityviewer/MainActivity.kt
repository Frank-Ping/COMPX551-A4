package com.example.polarh10activityviewer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polarh10activityviewer.ui.theme.PolarH10ActivityViewerTheme
import com.example.polarh10activityviewer.storage.SessionStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionStorage.get(applicationContext)
        enableEdgeToEdge()
        setContent {
            PolarH10ActivityViewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WelcomeScreen(
                        onEnterSession = {
                            startActivity(Intent(this@MainActivity, SensorActivity::class.java))
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(onEnterSession: () -> Unit, modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colorScheme
    val deviceName = stringResource(R.string.welcome_device_name)
    val productName = stringResource(R.string.welcome_product_name)
    BoxWithConstraints(modifier.fillMaxSize().background(colors.background)) {
        val viewportHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .heightIn(min = viewportHeight)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    BasicText(
                        deviceName,
                        maxLines = 1,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 24.sp,
                            lineHeight = 30.sp,
                            letterSpacing = .5.sp,
                            fontWeight = FontWeight.Medium,
                            color = colors.onBackground
                        ),
                        autoSize = TextAutoSize.StepBased(minFontSize = 16.sp, maxFontSize = 24.sp)
                    )
                    BasicText(
                        productName,
                        modifier = Modifier.padding(start = 12.dp),
                        maxLines = 1,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 40.sp,
                            lineHeight = 46.sp,
                            letterSpacing = .5.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.primary
                        ),
                        autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, maxFontSize = 40.sp)
                    )
                }
                // Decorative brand pulse, independent of recorded sensor data.
                Canvas(Modifier.weight(1f).height(30.dp)) {
                    val pulseWidth = size.width * .8f
                    val pulse = Path().apply {
                        moveTo(3.dp.toPx(), size.height * .50f)
                        lineTo(pulseWidth * .30f, size.height * .50f)
                        lineTo(pulseWidth * .39f, size.height * .25f)
                        lineTo(pulseWidth * .47f, size.height * .75f)
                        lineTo(pulseWidth * .56f, size.height * .10f)
                        lineTo(pulseWidth * .65f, size.height * .90f)
                        lineTo(pulseWidth * .74f, size.height * .50f)
                        lineTo(pulseWidth - 3.dp.toPx(), size.height * .50f)
                    }
                    translate(left = 12.dp.toPx()) {
                        drawPath(pulse, colors.primary.copy(alpha = .75f),
                            style = Stroke(2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round))
                    }
                }
            }
            Box(
                Modifier.padding(vertical = 32.dp).size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(Modifier.fillMaxSize()) {
                    drawCircle(colors.primary.copy(alpha = .08f), radius = size.width * .38f,
                        center = Offset(size.width * .55f, size.height * .43f))
                    drawOval(colors.primary.copy(alpha = .14f),
                        topLeft = Offset(size.width * .22f, size.height * .91f),
                        size = Size(size.width * .60f, size.height * .04f))
                    listOf(.35f, .46f, .59f).forEachIndexed { index, y ->
                        drawLine(colors.primary.copy(alpha = .35f),
                            Offset(size.width * (if (index == 1) .02f else .10f), size.height * y),
                            Offset(size.width * .20f, size.height * y), strokeWidth = 4.dp.toPx())
                    }
                }
                Image(painterResource(R.drawable.welcome_runner), contentDescription = null,
                    modifier = Modifier.fillMaxSize())
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        stringResource(R.string.welcome_tagline),
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground,
                        textAlign = TextAlign.Center
                    )
                    BasicText(
                        "Track your heart rate and movement during your activity.",
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 13.sp,
                            lineHeight = 18.sp,
                            letterSpacing = 0.sp,
                            color = colors.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        ),
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 8.sp, maxFontSize = 13.sp, stepSize = .5.sp)
                    )
                }
                Button(
                    onClick = onEnterSession,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
                    shape = CircleShape
                ) {
                    Text("Enter Session  →", modifier = Modifier.padding(vertical = 6.dp),
                        fontSize = 20.sp, fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    PolarH10ActivityViewerTheme {
        WelcomeScreen(onEnterSession = {})
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeDarkPreview() {
    PolarH10ActivityViewerTheme(darkTheme = true) {
        WelcomeScreen(onEnterSession = {})
    }
}
