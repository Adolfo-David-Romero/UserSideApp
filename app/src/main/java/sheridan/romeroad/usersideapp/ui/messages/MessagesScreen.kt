package sheridan.romeroad.usersideapp.ui.messages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 **/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages") },
                //backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Your Messages",
                style = MaterialTheme.typography.labelLarge
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(getMessages()) { message ->
                    MessageItem(message)
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        //elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = message.sender, style = MaterialTheme.typography.bodyMedium)
                Text(text = message.content, style = MaterialTheme.typography.bodySmall)
            }
            Text(text = message.time, style = MaterialTheme.typography.labelMedium)
        }
    }
}

// Mock data
data class Message(
    val sender: String,
    val content: String,
    val time: String
)

fun getMessages() = listOf(
    Message("Doctor Smith", "Remember to take your meds!", "10:45 AM"),
    Message("Family", "How are you feeling today?", "9:30 AM"),
    Message("Caregiver", "I'll visit you tomorrow.", "Yesterday")
)
