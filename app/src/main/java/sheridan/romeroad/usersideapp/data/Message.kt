package sheridan.romeroad.usersideapp.data

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-12-23
 **/
data class Message(
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

