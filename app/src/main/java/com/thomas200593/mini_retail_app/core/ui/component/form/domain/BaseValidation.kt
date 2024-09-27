package com.thomas200593.mini_retail_app.core.ui.component.form.domain

interface BaseValidation<String, ValidationResult> {
    fun execute(
        input: String,
        minLength: Int? = null,
        maxLength: Int? = null,
        required: Boolean? = false,
        regex: Regex? = null
    ): ValidationResult
}