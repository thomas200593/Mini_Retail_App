package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.SUCCESS
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AppAlertDialog

object DlgSuccess {
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
            showIcon = true,
            showTitle = true,
            title = { Text(text = stringResource(R.string.str_auth_success)) },
            useConfirmButton = true,
            confirmButton = {
                AppIconButton(
                    onClick = onConfirm,
                    icon = Icons.Default.Check,
                    text = stringResource(id = R.string.str_ok)
                )
            }
        )
    }
}