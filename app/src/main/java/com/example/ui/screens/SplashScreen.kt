package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyberBlack
import com.example.ui.theme.NeonCyan
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onAnimationComplete: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2000)
        onAnimationComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberBlack),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "ROOTMATE",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = NeonCyan,
                    letterSpacing = 8.sp,
                    fontWeight = FontWeight.Black
                ),
                modifier = Modifier.graphicsLayer(alpha = alphaAnim.value)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "INITIALIZING SYSTEM AUDIT...",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = NeonCyan.copy(alpha = 0.5f)
                ),
                modifier = Modifier.graphicsLayer(alpha = alphaAnim.value)
            )
        }
    }
}
