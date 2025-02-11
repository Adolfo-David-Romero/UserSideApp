package sheridan.romeroad.usersideapp.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import sheridan.romeroad.usersideapp.ui.gemini.ChatMessage
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
    private val patientStatusViewModel: PatientStatusViewModel
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun onInputChange(newInput: String) {
        _uiState.value = _uiState.value.copy(userInput = newInput)
    }

    fun sendRequest() {
        if (_uiState.value.userInput.isBlank()) return

        val userInput = _uiState.value.userInput
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null,
            chatHistory = _uiState.value.chatHistory + ChatMessage(isUser = true, text = userInput)
        )

        viewModelScope.launch {
            try {
                val patientStatusContext = patientStatusViewModel.patientStatus.value
                val userContext = suspendCancellableCoroutine<String> { continuation ->
                    profileViewModel.formatUserProfileForGemini(
                        userId = FirebaseAuth.getInstance().currentUser!!.uid,
                        onSuccess = { continuation.resume(it) },
                        onError = { continuation.resumeWithException(Exception(it)) }
                    )
                }
                val medicationContext = medicationViewModel.formatMedicationsForGemini()
                val prompt = content {
                    text("""
                You are a virtual assistant specialized in helping older adults and/or patients.
                Provide clear, empathetic, and easy-to-understand responses without using technical jargon.
                
                Patient Information: User Context: $userContext. User Status Context: Patient has type 2 diabetes, high blood pressure, and heart disease. User Medication Context: Medication 1 name: Insulin, dosage: 80mg, medication reminder: 4:00PM. Medication 2 name: Calcium channel blocker, Dosage: 5mg, Medication reminder: 8:00PM. . 
                
                User input: "$userInput"
            """.trimIndent())
                }
                val response = generativeModel.generateContent(prompt)

                _uiState.value = _uiState.value.copy(
                    userInput = "",
                    isLoading = false,
                    chatHistory = _uiState.value.chatHistory + ChatMessage(isUser = false, text = response.text.toString())
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

}

