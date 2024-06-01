package com.thomas200593.mini_retail_app.core.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thomas200593.mini_retail_app.core.ui.common.Icons.Google.google_logo

object Button {
    @Composable
    fun SignInWithGoogleButton(
        modifier: Modifier = Modifier,
        primaryText: String = "Sign in with Google",
        secondaryText: String = "Please wait...",
        googleIcon: Int = google_logo,
        btnLoadingState: Boolean = false,
        btnShape: Shape = MaterialTheme.shapes.medium,
        btnBorderStrokeWidth: Dp = 1.dp,
        btnBorderColor: Color = Color(0xFF747775),
        btnColor: Color = MaterialTheme.colorScheme.surface,
        btnShadowElevation: Dp = 9.dp,
        progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
        onClick: () -> Unit
    ){
        var btnText by remember { mutableStateOf(primaryText) }
        LaunchedEffect(btnLoadingState) {
            btnText = if(btnLoadingState) secondaryText else primaryText
        }
        Surface(
            modifier = modifier
                .clickable(
                    enabled = !btnLoadingState,
                    onClick = onClick
                ),
            shape = btnShape,
            border = BorderStroke(width = btnBorderStrokeWidth, color = btnBorderColor),
            color = btnColor,
            shadowElevation = btnShadowElevation
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(id = googleIcon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 2.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = btnText,
                    modifier = Modifier.padding(start = 10.dp, end = 2.dp)
                )
                if(btnLoadingState){
                    Spacer(modifier = Modifier.width(16.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                        color = progressIndicatorColor
                    )
                }
            }
        }
    }
}