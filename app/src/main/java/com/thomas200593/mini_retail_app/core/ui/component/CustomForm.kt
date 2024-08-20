package com.thomas200593.mini_retail_app.core.ui.component

import android.content.Context
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.NumberPassword
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.unit.dp
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.UiText
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.UiText.DynamicString
import com.thomas200593.mini_retail_app.core.ui.component.CustomForm.Component.UseCase.UiText.StringResource

object CustomForm {
    object Component {
        object UseCase {
            sealed class UiText {
                data class DynamicString(val value: String): UiText()
                class StringResource(@StringRes val resId: Int, vararg val args: Any): UiText()

                @Composable
                fun asString(): String = when(this) {
                    is DynamicString -> value
                    is StringResource -> stringResource(id = resId, *args)
                }

                fun asString(context: Context): String = when(this) {
                    is DynamicString -> value
                    is StringResource -> context.getString(resId, *args)
                }
            }
            data class ValidationResult(
                val isSuccess: Boolean,
                val errorMessage: UiText? = null
            )
            interface BaseValidation<String, ValidationResult> {
                fun execute(
                    input: String,
                    minLength: Int? = null,
                    maxLength: Int? = null,
                    required: Boolean? = false,
                    regex: Regex? = null
                ): ValidationResult
            }
            object InputFieldValidation {
                class RegularTextValidation: BaseValidation<String, ValidationResult> {
                    override fun execute(
                        input: String,
                        minLength: Int?,
                        maxLength: Int?,
                        required: Boolean?,
                        regex: Regex?
                    ): ValidationResult {
                        required?.let {
                            if (it && input.isEmpty()) {
                                return ValidationResult(
                                    isSuccess = false,
                                    errorMessage = StringResource(R.string.str_field_required)
                                )
                            }
                        }
                        minLength?.let {
                            if (input.length < it) {
                                return ValidationResult(
                                    isSuccess = false,
                                    errorMessage = DynamicString("Min: $minLength char")
                                )
                            }
                        }
                        maxLength?.let {
                            if (input.length > it) {
                                return ValidationResult(
                                    isSuccess = false,
                                    errorMessage = DynamicString("Max: $maxLength char")
                                )
                            }
                        }
                        regex?.let {
                            if (!input.matches(it)) {
                                return ValidationResult(
                                    isSuccess = false,
                                    errorMessage = DynamicString("Invalid format")
                                )
                            }
                        }
                        return ValidationResult(
                            isSuccess = true,
                            errorMessage = null
                        )
                    }
                }
                class NumberTextValidation: BaseValidation<String, ValidationResult> {
                    override fun execute(
                        input: String,
                        minLength: Int?,
                        maxLength: Int?,
                        required: Boolean?,
                        regex: Regex?
                    ): ValidationResult {
                        required?.let {
                            if (it && input.isEmpty()) {
                                return ValidationResult(
                                    isSuccess = false,
                                    errorMessage = StringResource(R.string.str_field_required)
                                )
                            }
                        }
                        val numberRegex = "^[0-9]+(\\.[0-9]+)?\$".toRegex()
                        if (!input.matches(numberRegex)) {
                            return ValidationResult(
                                isSuccess = false,
                                errorMessage = StringResource(R.string.str_field_invalid_number)
                            )
                        }
                        minLength?.let {
                            if (input.length < it) {
                                return ValidationResult(
                                    isSuccess = false,
                                    errorMessage = DynamicString("Min: $minLength char")
                                )
                            }
                        }
                        maxLength?.let {
                            if (input.length > it) {
                                return ValidationResult(
                                    isSuccess = false,
                                    errorMessage = DynamicString("Max: $maxLength char")
                                )
                            }
                        }
                        regex?.let {
                            if (!input.matches(it)) {
                                return ValidationResult(
                                    isSuccess = false,
                                    errorMessage = DynamicString("Invalid format")
                                )
                            }
                        }
                        return ValidationResult(
                            isSuccess = true,
                            errorMessage = null
                        )
                    }
                }
            }
        }

        @Composable
        fun TextInput(
            modifier: Modifier = Modifier,
            keyboardType: KeyboardType = Text,
            imeAction: ImeAction = Done,
            label: String? = String(),
            placeholder: String? = String(),
            value: String,
            onValueChange: (String) -> Unit,
            singleLine: (Boolean) = true,
            maxLines: Int = 1,
            errorMessage: UiText? = null,
            isError: Boolean = false,
            isVisible: Boolean = false,
            leadingIcon: @Composable (() -> Unit)? = null,
            trailingIcon: @Composable (() -> Unit)? = null,
        ){
            val context = LocalContext.current
            val interactionSource = remember { MutableInteractionSource() }
            val isFocused by interactionSource.collectIsFocusedAsState()
            val focusRequester = remember { FocusRequester() }
            val isTypePassword = (keyboardType == Password || keyboardType == NumberPassword)
            val visualTransformation by rememberUpdatedState(
                newValue = if(isTypePassword){
                    if(isVisible) { PasswordVisualTransformation() }
                    else { None }
                } else{ None }
            )
            val colorBorder by rememberUpdatedState(
                newValue = when{
                    isError -> { colorScheme.error }
                    isFocused -> { colorScheme.primary }
                    else -> { colorScheme.primary.copy(alpha = 0.3f) }
                }
            )
            val shouldShowLabel by
            remember(value) { derivedStateOf { value.isNotEmpty() && !label.isNullOrBlank() } }
            val placeholderText = placeholder.orEmpty()
            val labelText = label.orEmpty()

            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                AnimatedVisibility(
                    modifier = Modifier.fillMaxWidth(),
                    visible = shouldShowLabel
                ) {
                    Text(
                        text = labelText,
                        color = colorScheme.primary,
                        style = typography.labelSmall,
                        textAlign = Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                BasicTextField(
                    modifier = modifier.fillMaxWidth(),
                    value = value,
                    onValueChange = { newValue -> onValueChange(newValue) },
                    textStyle = typography.bodySmall.copy(color = colorScheme.onSurface),
                    maxLines = maxLines,
                    singleLine = singleLine,
                    interactionSource = interactionSource,
                    visualTransformation = visualTransformation,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction
                    ),
                    cursorBrush = SolidColor(colorScheme.primary),
                    decorationBox = { innerTextField ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = colorBorder
                            )
                            .background(
                                color = colorScheme.surface,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .focusRequester(focusRequester)) {
                            if (leadingIcon != null) { leadingIcon() }
                            else { Spacer(modifier = Modifier.padding(8.dp)) }
                            Box(modifier = Modifier.weight(1.0f).padding(vertical = 16.dp)) {
                                if (value.isEmpty()) {
                                    Text(
                                        text = placeholderText,
                                        style = typography.bodySmall,
                                        color = colorScheme.inversePrimary,
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
                        color = colorScheme.error,
                        style = typography.bodySmall,
                        textAlign = Start,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}