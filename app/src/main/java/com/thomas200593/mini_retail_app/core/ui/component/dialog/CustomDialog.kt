package com.thomas200593.mini_retail_app.core.ui.component.dialog

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
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy.Inherit
import com.thomas200593.mini_retail_app.core.ui.common.CustomIcons.Emotion.sad
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.CONFIRMATION
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.SUCCESS
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.WARNING

/**
 * A utility object that provides a customizable alert dialog component (`AppAlertDialog`)
 * with different contexts such as information, warning, error, success, and confirmation.
 */
object CustomDialog {
    /**
     * Enum class representing the context or type of alert dialog.
     */
    enum class AlertDialogContext{
        /** Information context for general notifications. */
        INFORMATION,
        /** Warning context for cautionary messages. */
        WARNING,
        /** Error context for displaying error messages. */
        ERROR,
        /** Success context for successful operations. */
        SUCCESS,
        /** Confirmation context for actions that require user confirmation. */
        CONFIRMATION
    }

    /**
     * Displays an alert dialog with a customizable design and behavior based on the provided
     * parameters and dialog context.
     *
     * @param modifier Modifier to be applied to the dialog.
     * @param showDialog State controlling the visibility of the dialog.
     * @param dialogContext The context/type of the dialog, such as information or error.
     * @param confirmButton A composable for the confirm button, if any.
     * @param dismissButton A composable for the dismiss button, if any.
     * @param title A composable for the dialog title, if any.
     * @param body A composable for the main body of the dialog, if any.
     * @param icon Optional icon to display in the dialog. If null, a default icon based on
     * the dialog context will be used.
     * @param dlgBoxShape The shape of the dialog's container.
     * @param dlgBoxTonalElev The tonal elevation applied to the dialog container.
     * @param dlgProperties Properties for the dialog's behavior, such as whether it can be
     * dismissed by clicking outside.
     */
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
                        //modifier = Modifier.size(48.dp)
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

    /**
     * Returns an icon to display based on the given dialog context.
     * @param dialogContext The context/type of the dialog.
     * @return ImageVector representing the icon for the specified dialog context.
     */
    @Composable
    private fun getIcon(dialogContext: AlertDialogContext) = when(dialogContext) {
        INFORMATION -> Default.Info
        WARNING -> Default.Warning
        ERROR -> ImageVector.vectorResource(id = sad)
        SUCCESS -> Default.CheckCircle
        CONFIRMATION -> Default.Create
    }

    /**
     * Returns the content color based on the given dialog context.
     * @param dialogContext The context/type of the dialog.
     * @return Color to use for the content of the specified dialog context.
     */
    @Composable
    private fun getContentColor(dialogContext: AlertDialogContext) = when(dialogContext) {
        INFORMATION -> colorScheme.onPrimaryContainer
        WARNING -> colorScheme.onSurface
        ERROR -> colorScheme.onErrorContainer
        SUCCESS -> colorScheme.onTertiaryContainer
        CONFIRMATION -> colorScheme.onSecondaryContainer
    }

    /**
     * Returns the container color based on the given dialog context.
     *
     * @param dialogContext The context/type of the dialog.
     * @return Color to use for the background of the specified dialog context.
     */
    @Composable
    private fun getContainerColor(dialogContext: AlertDialogContext) = when(dialogContext) {
        INFORMATION -> colorScheme.primaryContainer
        WARNING -> colorScheme.surfaceContainerHighest
        ERROR -> colorScheme.errorContainer
        SUCCESS -> colorScheme.tertiaryContainer
        CONFIRMATION -> colorScheme.secondaryContainer
    }
}