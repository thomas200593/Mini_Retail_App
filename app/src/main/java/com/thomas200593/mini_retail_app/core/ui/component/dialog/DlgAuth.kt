package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.ERROR
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.SUCCESS
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AppAlertDialog

object DlgAuth {
    @Composable
    fun AuthLoading(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>
    ) {
        AppAlertDialog(
            modifier = modifier,
            showDialog = showDialog,
            dialogContext = INFORMATION,
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
            },
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { Text(text = stringResource(id = R.string.str_authenticating)) }
            }
        )
    }

    @Composable
    fun AuthSuccess(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>,
        onConfirm: () -> Unit
    ) {
        AppAlertDialog(
            modifier = modifier,
            showDialog = showDialog,
            dialogContext = SUCCESS,
            title = { Text(text = stringResource(R.string.str_auth_success)) },
            confirmButton = {
                AppIconButton(
                    onClick = onConfirm,
                    icon = Icons.Default.Check,
                    text = stringResource(id = R.string.str_ok)
                )
            }
        )
    }

    @Composable
    fun AuthFailure(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>,
        throwable: Throwable,
        onDismiss: () -> Unit
    ) {
        AppAlertDialog(
            modifier = modifier,
            showDialog = showDialog,
            dialogContext = CustomDialog.AlertDialogContext.ERROR,
            title = { Text(text = stringResource(id = R.string.str_error)) },
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { Text(text = throwable.toString()) }
            },
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
            title = { Text(text = stringResource(id = R.string.str_error)) },
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { Text(text = stringResource(id = R.string.str_deny_access_auth_required)) }
            },
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