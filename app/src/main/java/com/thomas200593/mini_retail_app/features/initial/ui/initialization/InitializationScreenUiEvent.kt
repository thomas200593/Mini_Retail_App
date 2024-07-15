package com.thomas200593.mini_retail_app.features.initial.ui.initialization

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.ui.component.Form.Component.UseCase.InputFieldValidation
import com.thomas200593.mini_retail_app.core.ui.component.Form.Component.UseCase.UiText
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizName
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import ulid.ULID

class InitializationScreenUiEvent(private val vm: InitializationViewModel) {
    enum class InitializationUiEventStatus{ IDLE, LOADING, SUCCESS, FAILED }
    sealed class InitializationUiFormEvent{
        data class LegalNameChanged(val legalName: String): InitializationUiFormEvent()
        data class CommonNameChanged(val commonName: String): InitializationUiFormEvent()
        data object Submit: InitializationUiFormEvent()
    }
    data class InitializationUiFormState(
        val legalName: String = String(),
        val legalNameError: UiText? = UiText.StringResource(R.string.str_field_required),
        val commonName: String = String(),
        val commonNameError: UiText? = UiText.StringResource(R.string.str_field_required),
        val submitButtonEnabled: Boolean = false
    )
    var initializationUiFormState by mutableStateOf(InitializationUiFormState())
    private val validateLegalName = InputFieldValidation.RegularTextValidation()
    private val validateCommonName = InputFieldValidation.RegularTextValidation()
    fun onFormEvent(event: InitializationUiFormEvent){
        when(event){
            is InitializationUiFormEvent.LegalNameChanged -> {
                initializationUiFormState = initializationUiFormState.copy(legalName = event.legalName)
                validateLegalName();enableSubmitButton()
            }
            is InitializationUiFormEvent.CommonNameChanged -> {
                initializationUiFormState = initializationUiFormState.copy(commonName = event.commonName)
                validateCommonName();enableSubmitButton()
            }
            InitializationUiFormEvent.Submit -> {
                if(validateLegalName() && validateCommonName()){
                    vm.onSubmitManualInit(
                        BusinessProfileSummary(
                            seqId = 0,
                            genId = ULID.randomULID(),
                            bizName = BizName(
                                legalName = initializationUiFormState.legalName,
                                commonName = initializationUiFormState.commonName
                            ),
                            bizIndustry = null,
                            auditTrail = AuditTrail()
                        )
                    )
                }
            }
        }
    }
    private fun validateLegalName(): Boolean{
        val validationResult = validateLegalName
            .execute(input = initializationUiFormState.legalName, maxLength = 200, required = true)
        initializationUiFormState = initializationUiFormState.copy(legalNameError = validationResult.errorMessage)
        return validationResult.isSuccess
    }
    private fun validateCommonName(): Boolean{
        val validationResult = validateCommonName
            .execute(input = initializationUiFormState.commonName, maxLength = 200, required = true)
        initializationUiFormState = initializationUiFormState.copy(commonNameError = validationResult.errorMessage)
        return validationResult.isSuccess
    }
    private fun enableSubmitButton(): Boolean{
        val validationResult = validateLegalName() && validateCommonName()
        initializationUiFormState = initializationUiFormState.copy(submitButtonEnabled = validationResult)
        return validationResult
    }
}