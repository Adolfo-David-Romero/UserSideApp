package sheridan.romeroad.usersideapp.ui.gemini

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-12-20
 **/

data class ChatMessage(
    val isUser: Boolean, // True if it's a user's message, false if it's the assistant's response
    val text: String
)

data class UiState(
    val userInput: String = "",
    val response: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val chatHistory: List<ChatMessage> = emptyList() // Chat messages stored here
)
