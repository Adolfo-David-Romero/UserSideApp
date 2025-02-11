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
/*
class GeminiViewModel : ViewModel() {
    val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    @SuppressLint("SecretInSource")
    val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "AIzaSyB9UAjlNvIXjmpXI5Qld25O_D9E2j3kQL0",
    )

    fun sendPrompt(
        prompt: String
    ) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(prompt)
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }
}

class TextGeminiViewModel: ViewModel(){
    var textGeminiUiState: TextGeminiUiState by mutableStateOf(TextGeminiUiState.WelcomeState)
        private set
    @SuppressLint("SecretInSource")
    val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "AIzaSyB9UAjlNvIXjmpXI5Qld25O_D9E2j3kQL0",
    )
    fun makePrompt(){
        val prompt = "Ask about health issues"
        viewModelScope.launch{
            try{
                val result = generativeModel.generateContent(prompt)
                textGeminiUiState = TextGeminiUiState.SuccessState(result.text.toString())
            }catch (e: Exception){
                e.printStackTrace()
                textGeminiUiState = TextGeminiUiState.ErrorState

            }

        }
    }
}*/
