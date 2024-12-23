package sheridan.romeroad.usersideapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import sheridan.romeroad.usersideapp.ui.gemini.UiState
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-12-20
 * "AIzaSyB9UAjlNvIXjmpXI5Qld25O_D9E2j3kQL0"
 **/
class GeminiViewModel(
    private val generativeModel: GenerativeModel,
    private val medicationViewModel: MedicationViewModel,
    private val profileViewModel: ProfileViewModel,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun onInputChange(newInput: String) {
        _uiState.value = _uiState.value.copy(userInput = newInput)
    }

    fun sendRequest() {
        if (_uiState.value.userInput.isBlank()) return

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val medicationContext = medicationViewModel.formatMedicationsForGemini()
                val userContext = suspendCancellableCoroutine<String> { continuation ->
                    profileViewModel.formatUserProfileForGemini(
                        userId = FirebaseAuth.getInstance().currentUser!!.uid,
                        onSuccess = { continuation.resume(it) },
                        onError = { continuation.resumeWithException(Exception(it)) }
                    )
                }
                val prompt = content {
                    text("""
                    You are a virtual assistant specialized in helping older adults and/or patients.
                    Provide clear, empathetic, and easy-to-understand responses without using technical jargon. 
                    I will provide you the patient/user context, which will give you the background you need to understand their medical history, vitals, and any other relevant info. Use this information when relevant.
                    I will also provide you the user input directly from the user at the end.
                    
                    Patient Information: User Context: $userContext. Medication Context: $medicationContext. 
                    
                    User input: "${_uiState.value.userInput}"
                """.trimIndent())
                }
                val response = generativeModel.generateContent(prompt)
                _uiState.value = _uiState.value.copy(response = response.text, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}

