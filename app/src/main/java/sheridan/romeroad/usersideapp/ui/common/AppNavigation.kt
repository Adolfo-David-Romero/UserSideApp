package sheridan.romeroad.usersideapp.ui.common

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import sheridan.romeroad.usersideapp.ui.home.HomeScreen
import sheridan.romeroad.usersideapp.ui.medication.MedicationRemindersScreen
import sheridan.romeroad.usersideapp.ui.messages.MessagesScreen
import sheridan.romeroad.usersideapp.ui.profile.ProfileScreen
import sheridan.romeroad.usersideapp.ui.video.VideoFeedsScreen

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 **/
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(
            onSaveProfile = TODO(),
            onBack = { navController.popBackStack() }
        ) }
        composable("medications") { MedicationRemindersScreen() }
        composable("videos") { VideoFeedsScreen() }
        composable("messages") { MessagesScreen() }
    }
}