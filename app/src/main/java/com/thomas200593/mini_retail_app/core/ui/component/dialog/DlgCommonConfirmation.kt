package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.CONFIRMATION
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AppAlertDialog

object DlgCommonConfirmation {
    @Composable
    fun DlgConfirmationYesNo(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>,
        titleString: String = stringResource(R.string.str_confirm),
        bodyString: String = stringResource(R.string.str_confirm),
        onConfirm: () -> Unit,
        onDismiss: () -> Unit
    ) {
        AppAlertDialog(
            modifier = modifier,
            showDialog = showDialog,
            dialogContext = CONFIRMATION,
            title = { Text(titleString) },
            body = { Text(bodyString) },
            confirmButton = {
                AppIconButton(
                    onClick = onConfirm,
                    icon = Icons.Default.Check,
                    text = stringResource(id = R.string.str_yes)
                )
            },
            dismissButton = {
                AppIconButton(
                    onClick = onDismiss,
                    icon = Icons.Default.Close,
                    text = stringResource(id = R.string.str_no),
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            }
        )
    }
}

@Composable
@Preview
private fun PreviewConfirmationYesNo() = CustomThemes.ApplicationTheme {
    val showDialog = remember { mutableStateOf(true) }
    DlgCommonConfirmation.DlgConfirmationYesNo(showDialog = showDialog, onConfirm = {}, onDismiss = {})
}