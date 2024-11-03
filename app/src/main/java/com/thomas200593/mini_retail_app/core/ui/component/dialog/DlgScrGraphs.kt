package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.thomas200593.mini_retail_app.app.navigation.ScrGraphs
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
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
                    val lcState = rememberLazyListState()
                    LazyColumn(
                        state = lcState,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) { item { Text(text = stringResource(id = it)) } }
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

@Composable
@Preview
private fun PreviewScrDesc() = CustomThemes.ApplicationTheme {
    val showDialog = remember { mutableStateOf(true) }
    DlgScrGraphs.ScrDesc(
        showDialog = showDialog,
        currentScreen = ScrGraphs.ConfGenCurrency,
        onDismiss = {}
    )
}