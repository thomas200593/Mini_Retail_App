package com.thomas200593.mini_retail_app.core.ui.component

import android.content.Context
import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thomas200593.mini_retail_app.R
import java.util.regex.Pattern

object Form{
    object Component{
        object UseCase{
            sealed class UiText{
                data class DynamicString(val value: String): UiText()
                class StringResource(
                    @StringRes val resId: Int,
                    vararg val args: Any
                ): UiText()

                @Composable
                fun asString(): String =
                    when(this){
                        is DynamicString -> value
                        is StringResource -> stringResource(id = resId, *args)
                    }

                fun asString(context: Context): String =
                    when(this){
                        is DynamicString -> value
                        is StringResource -> context.getString(resId, *args)
                    }
            }
            data class ValidationResult(
                val isSuccess: Boolean,
                val errorMessage: UiText? = null
            )
            interface BaseValidation<String, ValidationResult>{
                suspend fun execute(input: String): ValidationResult
            }
            object InputFieldValidation{
                class EmailValidation: BaseValidation<String, ValidationResult>{
                    override suspend fun execute(input: String): ValidationResult {
                        if(
                            (input.isNotBlank()) &&
                            (input.isNotEmpty()) &&
                            (Patterns.EMAIL_ADDRESS.matcher(input).matches())
                        ){
                            return ValidationResult(
                                isSuccess = true,
                                errorMessage = null
                            )
                        }
                        return ValidationResult(
                            isSuccess = false,
                            errorMessage = UiText.StringResource(R.string.str_email_format_invalid)
                        )
                    }
                }
            }
        }

        @Composable
        fun TextInput(
            modifier: Modifier = Modifier,
            keyboardType: KeyboardType = KeyboardType.Text,
            imeAction: ImeAction = ImeAction.Done,
            label: String? = String(),
            value: String,
            onValueChange: (String) -> Unit,
            singleLine: (Boolean) = true,
            maxLines: Int = 1,
            errorMessage: UseCase.UiText? = null,
            isError: Boolean = false,
            isVisible: Boolean = false,
            leadingIcon: @Composable (() -> Unit)? = null,
            trailingIcon: @Composable (() -> Unit)? = null,
        ){
            val context = LocalContext.current
            val interactionSource = remember { MutableInteractionSource() }
            val isFocused by interactionSource.collectIsFocusedAsState()
            val focusRequester = remember { FocusRequester() }
            val isTypePassword =
                (
                    keyboardType == KeyboardType.Password ||
                    keyboardType == KeyboardType.NumberPassword
                )
            val visualTransformation = when(isTypePassword){
                true -> {
                    when(isVisible){
                        true -> { PasswordVisualTransformation() }
                        false -> { VisualTransformation.None }
                    }
                }
                false -> { VisualTransformation.None }
            }
            val colorBorder = if (isError) { MaterialTheme.colorScheme.error }
            else if (isFocused){ MaterialTheme.colorScheme.primary }
            else { MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) }
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = ((!label.isNullOrEmpty())) || (!label.isNullOrBlank())
                ) {
                    Text(
                        text = label.orEmpty(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = modifier,
                        textAlign = TextAlign.Start
                    )
                }
                BasicTextField(
                    modifier = modifier.fillMaxWidth(),
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = maxLines,
                    singleLine = singleLine,
                    interactionSource = interactionSource,
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType, imeAction = imeAction
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    decorationBox = { innerTextField ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = colorBorder
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .focusRequester(focusRequester)) {
                            if (leadingIcon != null) {
                                leadingIcon()
                            } else {
                                Spacer(modifier = Modifier.padding(8.dp))
                            }
                            Box(modifier = Modifier.weight(1.0f).padding(vertical = 16.dp)) {
                                if (value.isEmpty() || value.isBlank()) {
                                    Text(
                                        text = label.orEmpty(),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.inversePrimary,
                                    )
                                }
                                Box(modifier = Modifier.fillMaxWidth()) { innerTextField() }
                            }
                            if (trailingIcon != null) { trailingIcon() }
                            else { Spacer(modifier = Modifier.padding(8.dp)) }
                        }
                    }
                )
                AnimatedVisibility(visible = isError) {
                    Text(
                        text = if (isError) { errorMessage!!.asString(context) } else { String() },
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = modifier
                    )
                }
            }
        }
    }
}