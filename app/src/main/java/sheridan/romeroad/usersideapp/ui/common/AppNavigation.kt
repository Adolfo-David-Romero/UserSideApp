package sheridan.romeroad.usersideapp.ui.common

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.auth.ktx.*
import com.google.firebase.ktx.Firebase
import sheridan.romeroad.usersideapp.services.FallDetectionService
import sheridan.romeroad.usersideapp.ui.auth.LoginScreen
import sheridan.romeroad.usersideapp.ui.auth.RegisterScreen
//import sheridan.romeroad.usersideapp.ui.gemini.GeminiPromptScreen
import sheridan.romeroad.usersideapp.ui.gemini.GeminiScreen
import sheridan.romeroad.usersideapp.ui.gemini.GeminiViewModel
//import sheridan.romeroad.usersideapp.ui.gemini.TextGeminiViewModel
//import sheridan.romeroad.usersideapp.ui.gemini.UserPromptScreen
import sheridan.romeroad.usersideapp.ui.home.HomeScreen
import sheridan.romeroad.usersideapp.ui.medication.MedicationRemindersScreen
import sheridan.romeroad.usersideapp.ui.messages.MessagesScreen
import sheridan.romeroad.usersideapp.ui.profile.ProfileScreen
import sheridan.romeroad.usersideapp.ui.status.PatientStatusScreen
import sheridan.romeroad.usersideapp.ui.video.VideoFeedsScreen
//import sheridan.romeroad.usersideapp.ui.video.VideoFeedsScreen
import sheridan.romeroad.usersideapp.viewmodels.MedicationViewModel
import sheridan.romeroad.usersideapp.viewmodels.PatientStatusViewModel
import sheridan.romeroad.usersideapp.viewmodels.ProfileViewModel

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 **/
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val isLoggedIn = Firebase.auth.currentUser != null
    val profileViewModel: ProfileViewModel = viewModel()
    val medicationViewModel: MedicationViewModel = viewModel()
    val patientStatusViewModel: PatientStatusViewModel = viewModel()
    val geminiViewModel = GeminiViewModel(
        generativeModel = GenerativeModel(
            modelName = "gemini-1.5-pro",
            apiKey = "AIzaSyB9UAjlNvIXjmpXI5Qld25O_D9E2j3kQL0"
        )
    )
    val userId = Firebase.auth.currentUser?.uid
    val context = LocalContext.current

    // Start the fall detection service
    LaunchedEffect(Unit) {
        val intent = Intent(context, FallDetectionService::class.java)
        context.startService(intent)
    }
    NavHost(navController = navController, startDestination = if (isLoggedIn) "home" else "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") { popUpTo("login") { inclusive = true } } },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("patientStatus") {
            PatientStatusScreen(
                viewModel = patientStatusViewModel,
                medicationViewModel = medicationViewModel,
                patientId = userId ?: "defaultPatientId" // Replace with actual patient ID
            )
        }

        composable("medications") {
            MedicationRemindersScreen(
            viewModel = medicationViewModel,
            context = context
        ) }
        composable("gemini") {
            /*UserPromptScreen(
                geminiViewModel = geminiViewModel
            )*/
            GeminiScreen(viewModel = geminiViewModel)
        }

        composable("videos") {
            VideoFeedsScreen() }
        composable("register") {
            RegisterScreen(
                onRegistrationSuccess = { navController.navigate("login") { popUpTo("register") { inclusive = true } } },
                onBack = { navController.popBackStack() }
            )
        }
        composable("home") { HomeScreen(navController) }
        composable("profile") {

            if (userId != null) {
                ProfileScreen(
                    viewModel = profileViewModel,
                    userId = userId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        // Add other routes as needed
    }
}
