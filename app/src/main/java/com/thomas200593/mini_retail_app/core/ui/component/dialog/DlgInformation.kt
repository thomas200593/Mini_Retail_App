package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
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

object DlgInformation {
    @Composable
    fun Auth(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>
    ) {
        AppAlertDialog(
            modifier = modifier,
            showDialog = showDialog,
            dialogContext = INFORMATION,
            showTitle = true,
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
            },
            showBody = true,
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
    fun GetData(
        modifier: Modifier = Modifier,
        showDialog: MutableState<Boolean>
    ) {
        AppAlertDialog(
            modifier = modifier,
            showDialog = showDialog,
            dialogContext = INFORMATION,
            showTitle = true,
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { CircularProgressIndicator() }
            },
            showBody = true,
            body = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { Text(text = stringResource(id = R.string.str_loading_data)) }
            }
        )
    }

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
            showIcon = true,
            showTitle = true,
            title = { currentScreen.title?.let { Text(text = stringResource(id = it)) } },
            showBody = true,
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
            useDismissButton = true,
            dismissButton = {
                AppIconButton(
                    onClick = onDismiss,
                    icon = Icons.Default.Close,
                    text = stringResource(id = R.string.str_close)
                )
            }
        )
    }

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
            showIcon = true,
            showTitle = true,
            title = { Text(titleString) },
            showBody = true,
            body = { Text(bodyString) }
        )
    }
}