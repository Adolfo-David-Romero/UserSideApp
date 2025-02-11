package sheridan.romeroad.usersideapp.ui.messages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sheridan.romeroad.usersideapp.data.Message
import sheridan.romeroad.usersideapp.viewmodels.MessagingViewModel

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 **/


@Composable
fun MessagingScreen(

    viewModel: MessagingViewModel
) {

    val messages by viewModel.messages.collectAsState()
    val listState = rememberLazyListState()
    var messageText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.listenForMessages("patientId", "nurseId")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Chat History
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message = message, currentUserId = "patientId")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input Field and Send Button
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                label = { Text("Type your message") },
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(
                            Message(
                                senderId = "patientId",
                                receiverId = "nurseId",
                                text = messageText
                            ),
                            chatId = ""
                        )
                        messageText = ""
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message, currentUserId: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (message.senderId == currentUserId) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (message.senderId == currentUserId) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.senderId == currentUserId) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
