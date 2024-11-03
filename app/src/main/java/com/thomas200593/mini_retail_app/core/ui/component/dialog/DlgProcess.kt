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
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.SUCCESS
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AppAlertDialog

object DlgProcess {
    @Composable
    fun ProcessLoading(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>,
        titleString: String = stringResource(R.string.str_processing),
        bodyString: String = stringResource(R.string.str_processing)
    ) {
        AppAlertDialog(
            modifier = modifier,
            dialogContext = INFORMATION,
            showDialog = showDialog,
            title = { Text(titleString) },
            body = { Text(bodyString) }
        )
    }

    @Composable
    fun ProcessSuccess(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>,
        titleString: String = stringResource(R.string.str_success),
        bodyString: String = stringResource(R.string.str_biz_profile_init_success),
        onConfirm: () -> Unit
    ) {
        AppAlertDialog(
            modifier = modifier,
            dialogContext = SUCCESS,
            showDialog = showDialog,
            title = { Text(titleString) },
            body = { Text(bodyString) },
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