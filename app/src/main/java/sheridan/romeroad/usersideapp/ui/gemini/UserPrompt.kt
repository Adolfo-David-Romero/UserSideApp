package sheridan.romeroad.usersideapp.ui.gemini

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-12-11
 **/
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import sheridan.romeroad.usersideapp.viewmodels.GeminiViewModel

@Composable
fun GeminiScreen(viewModel: GeminiViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input field with larger font and voice input
        OutlinedTextField(
            value = uiState.userInput,
            onValueChange = viewModel::onInputChange,
            label = { Text("Chat with your AI Medical Assistant", style = MaterialTheme.typography.headlineSmall) },
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to send request with descriptive text
        Button(
            onClick = { viewModel.sendRequest() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Text(
                text = if (uiState.isLoading) "Processing..." else "Get Assistance",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the response with larger, readable text
        uiState.response?.let {
            Text(
                "Assistant's Response: $it",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Error handling with clear visibility
        uiState.error?.let {
            Text(
                "Error: $it",
                color = Color.Red,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
