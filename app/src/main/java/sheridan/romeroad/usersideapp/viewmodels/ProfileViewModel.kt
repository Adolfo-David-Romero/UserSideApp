package sheridan.romeroad.usersideapp.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
    private val storage = FirebaseStorage.getInstance()

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

    // Function to upload profile image to Firebase Storage
    fun uploadProfileImage(userId: String, imageUri: Uri, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val storageRef = storage.reference.child("profile_images/$userId.jpg")

        storageRef.putFile(imageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }.addOnFailureListener { onError(it.message ?: "Failed to get image URL") }
            }
            .addOnFailureListener { onError(it.message ?: "Image upload failed") }
    }

    // Function to update profile image URL in Firestore
    fun updateProfileImage(userId: String, imageUrl: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        userCollection.document(userId)
            .update("profileImageUrl", imageUrl)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Failed to update profile image") }
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
