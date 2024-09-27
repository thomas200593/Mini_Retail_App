package com.thomas200593.mini_retail_app.core.ui.component.form.state

data class ValidationResult(
    val isSuccess: Boolean,
    val errorMessage: UiText? = null
)