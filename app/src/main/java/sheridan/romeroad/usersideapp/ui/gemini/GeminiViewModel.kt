package sheridan.romeroad.usersideapp.ui.gemini

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.BuildConfig
//import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-12-20
 * "AIzaSyB9UAjlNvIXjmpXI5Qld25O_D9E2j3kQL0"
 **/
class GeminiViewModel(private val generativeModel: GenerativeModel) : ViewModel() {
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
                val prompt = content { text(_uiState.value.userInput) }
                val response = generativeModel.generateContent(prompt)
                _uiState.value = _uiState.value.copy(response = response.text, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}

