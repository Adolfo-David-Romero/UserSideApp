@file:OptIn(ExperimentalMaterial3Api::class)

package sheridan.romeroad.usersideapp.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import sheridan.romeroad.usersideapp.data.UserProfile
import sheridan.romeroad.usersideapp.viewmodels.ProfileViewModel

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 **/
//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    userId: String,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it
            viewModel.uploadProfileImage(userId, it, onSuccess = { imageUrl ->
                profileImageUrl = imageUrl
                viewModel.updateProfileImage(userId, imageUrl, {}, {})
            }, onError = { errorMessage = it })
        }
    }

    // Fetch user profile on screen load
    LaunchedEffect(userId) {
        viewModel.fetchUserProfile(
            userId = userId,
            onSuccess = { profile ->
                name = profile.name
                email = profile.email
                phone = profile.phone
                gender = profile.gender
                age = if (profile.age > 0) profile.age.toString() else ""
                profileImageUrl = profile.profileImageUrl
            },
            onError = { error -> errorMessage = error }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Circular Profile Image
                    Box(contentAlignment = Alignment.BottomEnd) {
                        if (profileImageUrl.isNotEmpty()) {
                            AsyncImage(
                                model = profileImageUrl,
                                contentDescription = "Profile Picture",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Gray, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Default Profile",
                                    tint = Color.White,
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        }

                        // Edit Button (Camera Icon)
                        IconButton(
                            onClick = { launcher.launch("image/*") },
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                                .border(2.dp, Color.White, CircleShape)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Profile Image", tint = Color.White)
                        }
                    }
                }
            }
            item {
                Text(
                    text = "Update your personal information",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Name Input
            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }

            // Email Input
            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }

            // Phone Input
            item {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }

            // Gender Input
            item {
                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Gender") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }

            // Age Input
            item {
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            // Save Button
            item {
                Button(
                    onClick = {
                        val userProfile = UserProfile(
                            name = name,
                            email = email,
                            phone = phone,
                            gender = gender,
                            age = age.toIntOrNull() ?: 0
                        )
                        viewModel.saveUserProfile(
                            userId = userId,
                            userProfile = userProfile,
                            onSuccess = { successMessage = "Profile saved successfully!" },
                            onError = { error -> errorMessage = error }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Save", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyLarge)
                }
            }

            // Error and Success Messages
            item {
                if (!errorMessage.isNullOrEmpty()) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (!successMessage.isNullOrEmpty()) {
                    Text(
                        text = successMessage!!,
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
