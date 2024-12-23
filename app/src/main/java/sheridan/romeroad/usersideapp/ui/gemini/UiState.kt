package sheridan.romeroad.usersideapp.ui.gemini

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-12-20
 **/
data class UiState(
    val userInput: String = "",
    val response: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
