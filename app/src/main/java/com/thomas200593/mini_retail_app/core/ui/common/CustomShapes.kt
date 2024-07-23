package com.thomas200593.mini_retail_app.core.ui.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode.Restart
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

object CustomShapes {
    @Composable
    fun DotsLoadingAnimation(
        modifier: Modifier = Modifier,
        circleSize: Dp = 16.dp,
        circleColor: Color = colorScheme.onSurface,
        spaceBetween: Dp = 10.dp,
        travelDistance: Dp = 20.dp
    ) {
        val circles = listOf(
            remember { Animatable(initialValue = 0f) },
            remember { Animatable(initialValue = 0f) },
            remember { Animatable(initialValue = 0f) }
        )

        circles.forEachIndexed { index, animationAble ->
            LaunchedEffect(key1 = animationAble) {
                delay(index * 100L)
                animationAble.animateTo(
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = keyframes {
                            durationMillis = 1200
                            0.0f at 0 using LinearOutSlowInEasing
                            1.0f at 300 using LinearOutSlowInEasing
                            0.0f at 600 using LinearOutSlowInEasing
                            0.0f at 1200 using LinearOutSlowInEasing
                        },
                        repeatMode = Restart
                    )
                )
            }
        }

        val circleValues = circles.map { it.value }
        val distance = with(LocalDensity.current) { travelDistance.toPx() }

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(spaceBetween)
        ) {
            circleValues.forEach { value ->
                Box(modifier = Modifier.graphicsLayer { translationY = -value * distance }
                    .size(circleSize).background(color = circleColor, shape = CircleShape)
                )
            }
        }
    }
}