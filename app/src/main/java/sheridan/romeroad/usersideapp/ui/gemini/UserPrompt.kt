package sheridan.romeroad.usersideapp.ui.gemini

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-12-11
 **/
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Chat history displayed in top-to-bottom order
        LazyColumn(
            state = listState, // Attach the list state for auto-scrolling
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Add spacing between messages
        ) {
            items(uiState.chatHistory) { message ->
                ChatBubble(message = message)
            }
        }

        // Auto-scroll to the bottom when a new message is added
        LaunchedEffect(uiState.chatHistory.size) {
            if (uiState.chatHistory.isNotEmpty()) {
                listState.animateScrollToItem(uiState.chatHistory.size - 1)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input field and send button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = uiState.userInput,
                onValueChange = viewModel::onInputChange,
                label = { Text("Type your message") },
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { viewModel.sendRequest() },
                enabled = !uiState.isLoading
            ) {
                Text(if (uiState.isLoading) "Sending..." else "Send")
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
