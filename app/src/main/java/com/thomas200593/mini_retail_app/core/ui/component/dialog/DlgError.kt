package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes.ApplicationTheme
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AppAlertDialog
import com.thomas200593.mini_retail_app.core.ui.component.dialog.DlgError.SessionInvalid

object DlgError {
    @Composable
    fun SessionInvalid(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>,
        onDismiss: () -> Unit
    ) {
        AppAlertDialog(
            modifier = modifier,
            showDialog = showDialog,
            dialogContext = ERROR,
            showTitle = true,
            title = { Text(text = stringResource(id = R.string.str_error)) },
            showBody = true,
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { Text(text = stringResource(id = R.string.str_deny_access_auth_required)) }
            },
            useDismissButton = true,
            dismissButton = {
                AppIconButton(
                    onClick = onDismiss,
                    icon = Icons.Default.Close,
                    text = stringResource(id = R.string.str_close),
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            }
        )
    }
}

@Composable
@Preview
private fun PreviewSessionInvalid() = ApplicationTheme {
    val showDialog = remember { mutableStateOf(true) }
    SessionInvalid(showDialog = showDialog, onDismiss = {})
}