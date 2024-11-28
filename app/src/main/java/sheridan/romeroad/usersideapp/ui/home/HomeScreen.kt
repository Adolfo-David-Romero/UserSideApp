package sheridan.romeroad.usersideapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 **/
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // State for controlling the drawer
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, drawerState)
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "Welcome Back!",
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            Color(0xFF6200EE),
                            Color.White
                        )
                    )
                }
            ) { innerPadding ->
                HomeContent(navController, innerPadding)
            }
        }
    )
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Solid background for drawer
            .padding(16.dp), // Ensure proper spacing
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "Navigation",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = Color(0xFF6200EE) // Accent color for the header
        )

        Divider(color = Color.Gray, thickness = 1.dp)

        DrawerButton("Profile") {
            scope.launch { drawerState.close() }
            navController.navigate("profile")
        }

        DrawerButton("Settings") {
            scope.launch { drawerState.close() }
            navController.navigate("settings") // Replace with actual route
        }

        DrawerButton("Help & Support") {
            scope.launch { drawerState.close() }
            navController.navigate("help_support") // Replace with actual route
        }
    }
}

@Composable
fun DrawerButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
            color = Color.Black
        )
    }
}

@Composable
fun HomeContent(navController: NavController, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Your Health Dashboard",
            style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold),
            color = Color(0xFF6200EE),
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            "Access your health information and services",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
            color = Color.Gray
        )

        HomeScreenButton(
            text = "Patient Status",
            onClick = { navController.navigate("patientStatus") }
        )

        HomeScreenButton(
            text = "Medication Reminders",
            onClick = { navController.navigate("medications") }
        )

        HomeScreenButton(
            text = "Video Feeds",
            onClick = { navController.navigate("videos") }
        )

        HomeScreenButton(
            text = "Messages",
            onClick = { navController.navigate("messages") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                Firebase.auth.signOut()
                navController.navigate("login") { popUpTo("home") { inclusive = true } }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
        ) {
            Text("Logout", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
        }
    }
}

@Composable
fun HomeScreenButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE), contentColor = Color.White),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold))
    }
}