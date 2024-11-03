package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.ui.component.CustomButton.Common.AppIconButton
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AlertDialogContext.INFORMATION
import com.thomas200593.mini_retail_app.core.ui.component.dialog.CustomDialog.AppAlertDialog

object DlgScrGraphs {
    @Composable
    fun ScrDesc(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>,
        currentScreen: ScrGraphs,
        onDismiss: () -> Unit
    ) {
        AppAlertDialog(
            modifier = modifier,
            dialogContext = INFORMATION,
            showDialog = showDialog,
            title = { currentScreen.title?.let { Text(text = stringResource(id = it)) } },
            body = {
                currentScreen.description?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) { Text(text = stringResource(id = it)) }
                }
            },
            dismissButton = {
                AppIconButton(
                    onClick = onDismiss,
                    icon = Icons.Default.Close,
                    text = stringResource(id = R.string.str_close)
                )
            }
        )
    }
}