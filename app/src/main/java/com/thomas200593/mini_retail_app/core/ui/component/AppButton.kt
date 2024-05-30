package com.thomas200593.mini_retail_app.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thomas200593.mini_retail_app.core.ui.common.AppTheme

object AppButton {
    @Composable
    fun SignInWithGoogleButton(
        modifier: Modifier = Modifier,
        primaryText: String = "Sign in with Google",
        secondaryText: String = "Please wait...",
        googleIcon: Int = 1,
        btnLoadingState: Boolean = false,
        btnShape: Shape = MaterialTheme.shapes.large,
        btnBorderStrokeWidth: Dp = 1.dp,
        btnBorderColor: Color = Color(0xFF747775),
        btnColor: Color = MaterialTheme.colorScheme.surface,
        btnShadowElevation: Dp = 9.dp,
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
            Text(text = "Test Button")
        }
    }
}

@Preview
@Composable
fun SignInWithGoogleButtonPreview(){
    AppTheme.ApplicationTheme {
        AppButton.SignInWithGoogleButton(
            onClick = {}
        )
    }
}