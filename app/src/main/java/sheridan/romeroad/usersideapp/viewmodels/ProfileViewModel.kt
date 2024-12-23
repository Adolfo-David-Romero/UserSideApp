package sheridan.romeroad.usersideapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch
import sheridan.romeroad.usersideapp.data.UserProfile

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-28
 **/


class ProfileViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")

    // Function to save or update user profile
    fun saveUserProfile(userId: String, userProfile: UserProfile, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                userCollection.document(userId).set(userProfile).await()
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "An unknown error occurred")
            }
        }
    }

    // Function to delete user profile
    fun deleteUserProfile(userId: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                userCollection.document(userId).delete().await()
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "An unknown error occurred")
            }
        }
    }
    fun formatUserProfileForGemini(userId: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        fetchUserProfile(
            userId = userId,
            onSuccess = { profile ->
                val formattedProfile = """
                Name: ${profile.name}
                Gender: ${profile.gender}
                Age: ${profile.age}
            """.trimIndent()
                onSuccess(formattedProfile)
            },
            onError = onError
        )
    }


    // Function to fetch user profile
    fun fetchUserProfile(userId: String, onSuccess: (UserProfile) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val snapshot = userCollection.document(userId).get().await()
                val userProfile = snapshot.toObject(UserProfile::class.java)
                if (userProfile != null) {
                    onSuccess(userProfile)
                } else {
                    onError("User profile not found")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An unknown error occurred")
            }
        }
    }
}
