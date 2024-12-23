package sheridan.romeroad.usersideapp.ui.gemini

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-12-11
 **/
import android.app.VoiceInteractor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Colors
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ResponseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GeminiScreen(viewModel: GeminiViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.userInput,
            onValueChange = viewModel::onInputChange,
            label = { Text("Enter your input") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.sendRequest() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Text(if (uiState.isLoading) "Loading..." else "Send")
        }

        Spacer(modifier = Modifier.height(16.dp))

        uiState.response?.let {
            Text("Response: $it", style = MaterialTheme.typography.bodyMedium)
        }

        uiState.error?.let {
            Text("Error: $it", color = Color.Red)
        }
    }
}