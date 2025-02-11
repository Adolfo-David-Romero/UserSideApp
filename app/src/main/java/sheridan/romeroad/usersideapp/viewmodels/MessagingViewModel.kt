package sheridan.romeroad.usersideapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import sheridan.romeroad.usersideapp.data.Message

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-12-20
 **/
class MessagingViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    // Listen for real-time message updates
    fun listenForMessages(patientId: String, nurseId: String) {
        val chatId = "$patientId$nurseId"
        db.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("MessagingViewModel", "Error listening for messages: ${error.message}")
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val fetchedMessages = snapshot.documents.mapNotNull { it.toObject(Message::class.java) }
                    _messages.value = fetchedMessages
                }
            }
    }

    // Send a new message
    fun sendMessage(message: Message, chatId: String) {
        db.collection("chats").document(chatId).collection("messages")
            .add(message)
            .addOnSuccessListener {
                Log.d("MessagingViewModel", "Message sent successfully")
            }
            .addOnFailureListener { error ->
                Log.e("MessagingViewModel", "Error sending message: ${error.message}")
            }
    }
}
