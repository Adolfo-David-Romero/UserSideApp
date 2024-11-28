package sheridan.romeroad.usersideapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 **/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") }
                // Uncomment the line below if you want a background color
                // backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        // Use Modifier.padding(it) to respect system insets like status/navigation bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it) // Scaffold insets
                .verticalScroll(rememberScrollState()) // Enables vertical scrolling
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { navController.navigate("profile") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Profile")
            }
            Button(
                onClick = { navController.navigate("medications") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Medication Reminders")
            }
            Button(
                onClick = { navController.navigate("videos") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Video Feeds")
            }
            Button(
                onClick = { navController.navigate("messages") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Messages")
            }
            // Add more items if needed
            Button(
                onClick = { /* Future navigation */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Settings")
            }
            Button(
                onClick = { /* Future navigation */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Help & Support")
            }

            /*Sign-out button*/
            Button(
                onClick = {
                    Firebase.auth.signOut()
                    navController.navigate("login") { popUpTo("home") { inclusive = true } }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }
}