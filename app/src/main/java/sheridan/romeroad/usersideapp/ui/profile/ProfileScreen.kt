package sheridan.romeroad.usersideapp.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sheridan.romeroad.usersideapp.data.UserProfile
import sheridan.romeroad.usersideapp.viewmodels.ProfileViewModel

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 **/
@Composable
fun ProfileScreen(viewModel: ProfileViewModel, userId: String, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userId) {
        // Fetch user profile when the screen is loaded
        viewModel.fetchUserProfile(userId,
            onSuccess = { profile ->
                name = profile.name
                email = profile.email
                phone = profile.phone
            },
            onError = { errorMessage = it }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Edit Profile",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 18.sp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 18.sp)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            textStyle = TextStyle(fontSize = 18.sp)
        )

        Button(
            onClick = {
                val userProfile = UserProfile(name, email, phone)
                viewModel.saveUserProfile(userId, userProfile,
                    onSuccess = { successMessage = "Profile saved successfully!" },
                    onError = { errorMessage = it }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Green),
        ) {
            Text(text = "Save", color = Color.White, fontSize = 18.sp)
        }

        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Gray),
        ) {
            Text(text = "Back", color = Color.White, fontSize = 18.sp)
        }

        errorMessage?.let {
            Text(text = it, color = Color.Red)
        }

        successMessage?.let {
            Text(text = it, color = Color.Green)
        }
    }
}