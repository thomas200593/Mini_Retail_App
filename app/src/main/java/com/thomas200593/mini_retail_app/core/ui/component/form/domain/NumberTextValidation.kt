package com.thomas200593.mini_retail_app.core.ui.component.form.domain

import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.component.form.state.UiText
import com.thomas200593.mini_retail_app.core.ui.component.form.state.ValidationResult

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
                    errorMessage = UiText.StringResource(R.string.str_field_required)
                )
            }
        }
        val numberRegex = "^[0-9]+(\\.[0-9]+)?\$".toRegex()
        if (!input.matches(numberRegex)) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = UiText.StringResource(R.string.str_field_invalid_number)
            )
        }
        minLength?.let {
            if (input.length < it) {
                return ValidationResult(
                    isSuccess = false,
                    errorMessage = UiText.DynamicString("Min: $minLength char")
                )
            }
        }
        maxLength?.let {
            if (input.length > it) {
                return ValidationResult(
                    isSuccess = false,
                    errorMessage = UiText.DynamicString("Max: $maxLength char")
                )
            }
        }
        regex?.let {
            if (!input.matches(it)) {
                return ValidationResult(
                    isSuccess = false,
                    errorMessage = UiText.StringResource(R.string.str_invalid_format)
                )
            }
        }
        return ValidationResult(
            isSuccess = true,
            errorMessage = null
        )
    }
}