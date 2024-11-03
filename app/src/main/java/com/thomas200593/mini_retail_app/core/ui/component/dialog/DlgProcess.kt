package com.thomas200593.mini_retail_app.core.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.common.CustomThemes
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
        bodyString: String = stringResource(R.string.str_success),
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

    @Composable
    fun CustomDialogUI(
        modifier: Modifier = Modifier,
        onDialogStateChanged: () -> Unit,
        onClick: () -> Unit,
        title: String,
        description: String,
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = modifier.background(colorResource(R.color.charcoal_gray))
            ) {

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 5.dp).fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        color = colorResource(R.color.starting_day),
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = description,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(R.color.starting_day)

                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(colorResource(R.color.charcoal_gray)),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(onClick = {  }) {

                        Text(
                            "Dismiss",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                    TextButton(onClick = {
                        onClick()
                    }) {
                        Text(
                            "Yes",
                            fontWeight = FontWeight.ExtraBold,
                            color = colorResource(R.color.starting_day),
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewProcessLoading() = CustomThemes.ApplicationTheme {
    val showDialog = remember { mutableStateOf(true) }
    DlgProcess.ProcessLoading(showDialog = showDialog)
}

@Composable
@Preview
private fun PreviewProcessSuccess() = CustomThemes.ApplicationTheme {
    val showDialog = remember { mutableStateOf(true) }
    DlgProcess.ProcessSuccess (showDialog = showDialog, onConfirm = {})
}

@Composable
@Preview
private fun PreviewCustomDialogUI() = CustomThemes.ApplicationTheme {
    DlgProcess.CustomDialogUI(
        onClick = {},
        title = "Test Dialog",
        onDialogStateChanged = {},
        description = "Test Description"
    )
}