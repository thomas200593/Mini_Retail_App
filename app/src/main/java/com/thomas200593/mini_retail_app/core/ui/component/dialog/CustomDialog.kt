package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.TonalElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy.Inherit
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Emotion.sad
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.CONFIRMATION
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.SUCCESS
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.WARNING

object CustomDialog {
    /**
     * Enum class that defines different contexts for the [AppAlertDialog].
     * Each context specifies the purpose of the dialog and affects the icon and color scheme used.
     */
    enum class AlertDialogContext{ INFORMATION, WARNING, ERROR, SUCCESS, CONFIRMATION }

    @Composable
    fun AppAlertDialog(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>,
        dialogContext: AlertDialogContext = INFORMATION,
        confirmButton: (@Composable (() -> Unit))? = null,
        dismissButton: (@Composable (() -> Unit))? = null,
        title: (@Composable (() -> Unit))? = null,
        body: (@Composable (() -> Unit))? = null,
        icon: ImageVector? = null,
        dlgBoxShape: Shape = MaterialTheme.shapes.medium,
        dlgBoxTonalElev: Dp = TonalElevation,
        dlgProperties: DialogProperties = DialogProperties(
            dismissOnClickOutside = false,
            securePolicy = Inherit,
            dismissOnBackPress = false,
            decorFitsSystemWindows = true,
            usePlatformDefaultWidth = true
        )
    ) {
        if (showDialog.value) {
            AlertDialog(
                modifier = modifier,
                properties = dlgProperties,
                shape = dlgBoxShape,
                onDismissRequest = { showDialog.value = false },
                containerColor = getContainerColor(dialogContext),
                iconContentColor = getContentColor(dialogContext),
                textContentColor = getContentColor(dialogContext),
                tonalElevation = dlgBoxTonalElev,
                icon = {
                    Surface(
                        color = getContainerColor(dialogContext),
                        contentColor = getContentColor(dialogContext),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(imageVector = icon ?: getIcon(dialogContext), contentDescription = null)
                    }
                },
                title = { title?.invoke() },
                text = { body?.invoke() },
                confirmButton = { confirmButton?.invoke() },
                dismissButton = { dismissButton?.invoke() }
            )
        }
    }

    @Composable
    private fun getIcon(dialogContext: AlertDialogContext) = when(dialogContext) {
        INFORMATION -> Default.Info
        WARNING -> Default.Warning
        ERROR -> ImageVector.vectorResource(id = sad)
        SUCCESS -> Default.CheckCircle
        CONFIRMATION -> Default.Create
    }

    @Composable
    private fun getContentColor(dialogContext: AlertDialogContext) = when(dialogContext) {
        INFORMATION -> colorScheme.onPrimaryContainer
        WARNING -> colorScheme.onSurface
        ERROR -> colorScheme.onErrorContainer
        SUCCESS -> colorScheme.onTertiaryContainer
        CONFIRMATION -> colorScheme.onSecondaryContainer
    }

    @Composable
    private fun getContainerColor(dialogContext: AlertDialogContext) = when(dialogContext) {
        INFORMATION -> colorScheme.primaryContainer
        WARNING -> colorScheme.surfaceContainerHighest
        ERROR -> colorScheme.errorContainer
        SUCCESS -> colorScheme.tertiaryContainer
        CONFIRMATION -> colorScheme.secondaryContainer
    }
}