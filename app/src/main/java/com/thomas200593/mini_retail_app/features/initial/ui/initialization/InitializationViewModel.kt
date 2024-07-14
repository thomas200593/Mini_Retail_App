package com.thomas200593.mini_retail_app.features.initial.ui.initialization

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomas200593.mini_retail_app.R
import com.thomas200593.mini_retail_app.core.data.local.database.entity_common.AuditTrail
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.dispatchers.Dispatchers
import com.thomas200593.mini_retail_app.core.design_system.util.RequestState
import com.thomas200593.mini_retail_app.core.ui.component.Form.Component.UseCase.InputFieldValidation.RegularTextValidation
import com.thomas200593.mini_retail_app.core.ui.component.Form.Component.UseCase.UiText
import com.thomas200593.mini_retail_app.features.app_config.entity.Language
import com.thomas200593.mini_retail_app.features.app_config.repository.ConfigGeneralRepository
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.BizName
import com.thomas200593.mini_retail_app.features.business.entity.business_profile.dto.BusinessProfileSummary
import com.thomas200593.mini_retail_app.features.initial.domain.GetInitializationDataUseCase
import com.thomas200593.mini_retail_app.features.initial.domain.SetDefaultInitialBizProfileUseCase
import com.thomas200593.mini_retail_app.features.initial.entity.Initialization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ulid.ULID
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class InitializationViewModel @Inject constructor(
    private val cfgGeneralRepository: ConfigGeneralRepository,
    private val getUiStateUseCase: GetInitializationDataUseCase,
    private val setDefaultUseCase: SetDefaultInitialBizProfileUseCase,
    private val savedStateHandle: SavedStateHandle,
    @Dispatcher(Dispatchers.Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {

    companion object{
        private const val KEY_SHOW_WELCOME_MESSAGE = "showWelcomeMessage"
        private const val KEY_SHOW_INPUT_MANUAL_FORM = "showInputManualForm"
        private const val KEY_INIT_BUSINESS_PROFILE_PROGRESS = "initBizProfileProgress"
        enum class Status{ IDLE, LOADING, SUCCESS, FAILED }
    }

    var showWelcomeMessage by mutableStateOf(savedStateHandle[KEY_SHOW_WELCOME_MESSAGE] ?: true)
        private set

    var showInputManualForm by mutableStateOf(savedStateHandle[KEY_SHOW_INPUT_MANUAL_FORM] ?: false)
        private set

    private val _initBizProfileProgress: MutableStateFlow<String> = MutableStateFlow(savedStateHandle[KEY_INIT_BUSINESS_PROFILE_PROGRESS] ?: Status.IDLE.name)
    val initBizProfileProgress: StateFlow<String> get() = _initBizProfileProgress

    private val _uiState: MutableState<RequestState<Initialization>> = mutableStateOf(RequestState.Idle)
    val uiState = _uiState

    val initBizProfileManual = InitBizProfileManual(this)

    fun onOpen() = viewModelScope.launch(ioDispatcher) {
        getUiState()
    }

    private fun getUiState() = viewModelScope.launch(ioDispatcher){
        _uiState.value = RequestState.Loading
        try{ getUiStateUseCase.invoke().flowOn(ioDispatcher).collect{ data -> _uiState.value = data } }
        catch (e:Throwable){ _uiState.value = RequestState.Error(e) }
    }

    fun setLanguage(language: Language) = viewModelScope.launch(ioDispatcher) {
        cfgGeneralRepository.setLanguage(language)
        AppCompatDelegate.setApplicationLocales( LocaleListCompat.create(Locale(language.code)) )
    }

    fun onBeginManualInit() = viewModelScope.launch(ioDispatcher) {
        showWelcomeMessage = false;savedStateHandle[KEY_SHOW_WELCOME_MESSAGE] = false
        showInputManualForm = true;savedStateHandle[KEY_SHOW_INPUT_MANUAL_FORM] = true
    }

    fun onCancelManualInit() = viewModelScope.launch(ioDispatcher) {
        initBizProfileManual.formState = InitBizProfileManual.FormState()
        showWelcomeMessage = true;savedStateHandle[KEY_SHOW_WELCOME_MESSAGE] = true
        showInputManualForm = false;savedStateHandle[KEY_SHOW_INPUT_MANUAL_FORM] = false
    }

    fun setBizProfileDefault(businessProfileSummary: BusinessProfileSummary) = viewModelScope.launch(ioDispatcher) {
        _initBizProfileProgress.value = Status.LOADING.name
        savedStateHandle[KEY_INIT_BUSINESS_PROFILE_PROGRESS] = Status.LOADING.name

        _initBizProfileProgress.value = try{
            val result = setDefaultUseCase.invoke(businessProfileSummary)
            if(result !=null){
                savedStateHandle[KEY_INIT_BUSINESS_PROFILE_PROGRESS] = Status.SUCCESS.name
                Status.SUCCESS.name
            }else{
                savedStateHandle[KEY_INIT_BUSINESS_PROFILE_PROGRESS] = Status.FAILED.name
                Status.FAILED.name
            }
        }catch (e: Throwable){
            savedStateHandle[KEY_INIT_BUSINESS_PROFILE_PROGRESS] = Status.FAILED.name
            Status.FAILED.name
        }
    }

    fun onSubmitManualInit(businessProfileSummary: BusinessProfileSummary) = viewModelScope.launch(ioDispatcher) {
        _initBizProfileProgress.value = Status.LOADING.name
        savedStateHandle[KEY_INIT_BUSINESS_PROFILE_PROGRESS] = Status.LOADING.name

        _initBizProfileProgress.value = try{
            val result = setDefaultUseCase.invoke(businessProfileSummary)
            if(result != null){
                savedStateHandle[KEY_INIT_BUSINESS_PROFILE_PROGRESS] = Status.SUCCESS.name
                Status.SUCCESS.name
            }else{
                savedStateHandle[KEY_INIT_BUSINESS_PROFILE_PROGRESS] = Status.FAILED.name
                Status.FAILED.name
            }
        }catch (e: Throwable){
            savedStateHandle[KEY_INIT_BUSINESS_PROFILE_PROGRESS] = Status.FAILED.name
            Status.FAILED.name
        }
    }

    class InitBizProfileManual(private val vm: InitializationViewModel) {
        sealed class FormEvent{
            data class LegalNameChanged(val legalName: String): FormEvent()
            data class CommonNameChanged(val commonName: String): FormEvent()
            data object Submit: FormEvent()
        }
        data class FormState(
            val legalName: String = String(),
            val legalNameError: UiText? = UiText.StringResource(R.string.str_field_required),
            val commonName: String = String(),
            val commonNameError: UiText? = UiText.StringResource(R.string.str_field_required),
            val submitButtonEnabled: Boolean = false
        )
        var formState by mutableStateOf(FormState())
        private val validateLegalName = RegularTextValidation()
        private val validateCommonName = RegularTextValidation()
        fun onEvent(event: FormEvent){
            when(event){
                is FormEvent.LegalNameChanged -> {
                    formState = formState.copy(legalName = event.legalName)
                    validateLegalName();enableSubmitButton()
                }
                is FormEvent.CommonNameChanged -> {
                    formState = formState.copy(commonName = event.commonName)
                    validateCommonName();enableSubmitButton()
                }
                FormEvent.Submit -> {
                    if(validateLegalName() && validateCommonName()){
                        vm.onSubmitManualInit(BusinessProfileSummary(
                            seqId = 0,
                            genId = ULID.randomULID(),
                            bizName = BizName(legalName = formState.legalName, commonName = formState.commonName),
                            bizIndustry = null,
                            auditTrail = AuditTrail()
                        ))
                    }
                }
            }
        }
        private fun validateLegalName(): Boolean{
            val validationResult = validateLegalName
                .execute(input = formState.legalName, maxLength = 200, required = true)
            formState = formState.copy(legalNameError = validationResult.errorMessage)
            return validationResult.isSuccess
        }
        private fun validateCommonName(): Boolean{
            val validationResult = validateCommonName
                .execute(input = formState.commonName, maxLength = 200, required = true)
            formState = formState.copy(commonNameError = validationResult.errorMessage)
            return validationResult.isSuccess
        }
        private fun enableSubmitButton(): Boolean{
            val validationResult = validateLegalName() && validateCommonName()
            formState = formState.copy(submitButtonEnabled = validationResult)
            return validationResult
        }
    }
}