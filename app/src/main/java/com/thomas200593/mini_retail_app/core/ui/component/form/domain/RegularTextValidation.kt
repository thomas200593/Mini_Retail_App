package com.thomas200593.mini_retail_app.core.ui.component.form.domain

import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.ui.component.form.state.UiText
import com.thomas200593.mini_retail_app.core.ui.component.form.state.ValidationResult

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
                    errorMessage = UiText.StringResource(R.string.str_field_required)
                )
            }
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
                    errorMessage = UiText.DynamicString("Invalid format")
                )
            }
        }
        return ValidationResult(
            isSuccess = true,
            errorMessage = null
        )
    }
}