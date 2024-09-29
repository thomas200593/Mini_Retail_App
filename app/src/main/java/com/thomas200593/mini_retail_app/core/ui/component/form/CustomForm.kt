package com.thomas200593.mini_retail_app.core.ui.component.form

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thomas200593.mini_retail_app.core.ui.component.form.state.UiText

object CustomForm {
    @Composable
    fun TextInput(
        modifier: Modifier = Modifier,
        keyboardType: KeyboardType = KeyboardType.Text,
        imeAction: ImeAction = ImeAction.Done,
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
        val isTypePassword = (keyboardType == KeyboardType.Password || keyboardType == KeyboardType.NumberPassword)
        val visualTransformation by rememberUpdatedState(
            newValue = if(isTypePassword) {
                if(isVisible) { PasswordVisualTransformation() }
                else { VisualTransformation.None }
            }
            else { VisualTransformation.None }
        )
        val colorBorder by rememberUpdatedState(
            newValue = when{
                isError -> { MaterialTheme.colorScheme.error }
                isFocused -> { MaterialTheme.colorScheme.primary }
                else -> { MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) }
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
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            BasicTextField(
                modifier = modifier.fillMaxWidth(),
                value = value,
                onValueChange = { newValue -> onValueChange(newValue) },
                textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
                maxLines = maxLines,
                singleLine = singleLine,
                interactionSource = interactionSource,
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
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
                        if(leadingIcon != null) leadingIcon()
                        else Spacer(modifier = Modifier.padding(8.dp))
                        Box(modifier = Modifier.weight(1.0f).padding(vertical = 16.dp)) {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholderText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.inversePrimary,
                                )
                            }
                            Box(modifier = Modifier.fillMaxWidth()) { innerTextField() }
                        }
                        if (trailingIcon != null) trailingIcon()
                        else Spacer(modifier = Modifier.padding(8.dp))
                    }
                }
            )
            AnimatedVisibility(visible = isError) {
                Text(
                    text = if (isError) errorMessage!!.asString(context) else String(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}