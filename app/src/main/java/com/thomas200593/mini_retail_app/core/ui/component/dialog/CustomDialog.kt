package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.foundation.Image
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
import androidx.compose.ui.window.SecureFlagPolicy
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

    /**
     * Multi purpose custom [AlertDialog]
     * @param modifier Modifier to be applied to the AlertDialog.
     * @param showDialog MutableState that determines whether to show or hide the AlertDialog.
     * @param dialogContext The context of the dialog.
     * Possible values are:
     * - [AlertDialogContext.INFORMATION]
     * - [AlertDialogContext.WARNING]
     * - [AlertDialogContext.ERROR]
     * - [AlertDialogContext.SUCCESS]
     * - [AlertDialogContext.CONFIRMATION]
     * @param useConfirmButton Whether to use the confirm button.
     * @param confirmButton Custom composable for the confirm button.
     * @param useDismissButton Whether to use the dismiss button.
     * @param dismissButton Custom composable for the dismiss button.
     * @param showIcon Whether to show the icon.
     * @param icon Custom icon to be displayed.
     * @param showTitle Whether to show the title.
     * @param title Custom composable for the title.
     * @param showBody Whether to show the body.
     * @param body Custom composable for the body.
     * @param dlgPropDismissOnClickOutside Whether to dismiss the dialog when clicked outside.
     * @param dlgPropDismissOnBackPress Whether to dismiss the dialog on back press.
     * @param dlgPropDecorFitsSysWindows Whether the dialog decor fits system windows.
     * @param dlgPropUsePlatformDefaultWidth Whether to use platform default width.
     * @param dlgPropSecureFlagPolicy Secure flag policy for the dialog.
     * @param dlgBoxShape Shape of the dialog box.
     * @param dlgBoxTonalElev Tonal elevation of the dialog box.
     */
    @Composable
    fun AppAlertDialog(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>,
        dialogContext: AlertDialogContext = INFORMATION,
        useConfirmButton: Boolean = false,
        confirmButton: (@Composable (()->Unit))? = null,
        useDismissButton: Boolean = false,
        dismissButton: (@Composable (()->Unit))? = null,
        showIcon: Boolean = false,
        icon: ImageVector? = null,
        showTitle: Boolean = false,
        title: (@Composable (()->Unit))? = null,
        showBody: Boolean = false,
        body: (@Composable (()->Unit))? = null,
        dlgPropDismissOnClickOutside: Boolean = false,
        dlgPropDismissOnBackPress: Boolean = false,
        dlgPropDecorFitsSysWindows: Boolean = true,
        dlgPropUsePlatformDefaultWidth: Boolean = true,
        dlgPropSecureFlagPolicy: SecureFlagPolicy = Inherit,
        dlgBoxShape: Shape = MaterialTheme.shapes.medium,
        dlgBoxTonalElev: Dp = TonalElevation
    ){
        if(showDialog.value){
            AlertDialog(
                modifier = modifier,
                properties = DialogProperties(
                    dismissOnClickOutside = dlgPropDismissOnClickOutside,
                    securePolicy = dlgPropSecureFlagPolicy,
                    dismissOnBackPress = dlgPropDismissOnBackPress,
                    decorFitsSystemWindows = dlgPropDecorFitsSysWindows,
                    usePlatformDefaultWidth = dlgPropUsePlatformDefaultWidth
                ),
                shape = dlgBoxShape,
                onDismissRequest = { showDialog.value = false },
                titleContentColor = when(dialogContext){
                    INFORMATION -> { colorScheme.onPrimaryContainer }
                    WARNING -> { colorScheme.onSurface }
                    ERROR -> { colorScheme.onErrorContainer }
                    SUCCESS -> { colorScheme.onTertiaryContainer }
                    CONFIRMATION -> { colorScheme.onSecondaryContainer }
                },
                icon = {
                    if(showIcon){
                        Surface(
                            color = when(dialogContext){
                                INFORMATION -> { colorScheme.primaryContainer }
                                WARNING -> { colorScheme.surfaceContainerHighest }
                                ERROR -> { colorScheme.errorContainer }
                                SUCCESS -> { colorScheme.tertiaryContainer }
                                CONFIRMATION -> { colorScheme.secondaryContainer }
                            },
                            contentColor = when(dialogContext){
                                INFORMATION -> { colorScheme.onPrimaryContainer }
                                WARNING -> { colorScheme.onSurface }
                                ERROR -> { colorScheme.onErrorContainer }
                                SUCCESS -> { colorScheme.onTertiaryContainer }
                                CONFIRMATION -> { colorScheme.onSecondaryContainer }
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            if(icon != null){ Image(imageVector = icon, contentDescription = null) }
                            else{
                                when(dialogContext){
                                    INFORMATION ->
                                        { Icon(imageVector = Default.Info, contentDescription = null) }
                                    WARNING ->
                                        { Icon(imageVector = Default.Warning, contentDescription = null) }
                                    ERROR ->
                                        { Icon(imageVector = ImageVector.vectorResource(id = sad), contentDescription = null) }
                                    SUCCESS ->
                                        { Icon(imageVector = Default.CheckCircle, contentDescription = null) }
                                    CONFIRMATION ->
                                        { Icon(imageVector = Default.Create, contentDescription = null) }
                                }
                            }
                        }
                    }
                },
                containerColor = when(dialogContext){
                    INFORMATION -> { colorScheme.primaryContainer }
                    WARNING -> { colorScheme.surfaceContainerHighest }
                    ERROR -> { colorScheme.errorContainer }
                    SUCCESS -> { colorScheme.tertiaryContainer }
                    CONFIRMATION -> { colorScheme.secondaryContainer }
                },
                iconContentColor = when(dialogContext){
                    INFORMATION -> { colorScheme.onPrimaryContainer }
                    WARNING -> { colorScheme.onSurface }
                    ERROR -> { colorScheme.onErrorContainer }
                    SUCCESS -> { colorScheme.onTertiaryContainer }
                    CONFIRMATION -> { colorScheme.onSecondaryContainer }
                },
                textContentColor = when(dialogContext){
                    INFORMATION -> { colorScheme.onPrimaryContainer }
                    WARNING -> { colorScheme.onSurface }
                    ERROR -> { colorScheme.onErrorContainer }
                    SUCCESS -> { colorScheme.onTertiaryContainer }
                    CONFIRMATION -> { colorScheme.onSecondaryContainer }
                },
                tonalElevation = dlgBoxTonalElev,
                title = { if(showTitle && (title != null)) { title() } },
                text = { if(showBody && (body != null)) { body() } },
                confirmButton = { if(useConfirmButton && (confirmButton != null)) { confirmButton() } },
                dismissButton = { if(useDismissButton && (dismissButton != null)) { dismissButton() } },
            )
        }
    }
}