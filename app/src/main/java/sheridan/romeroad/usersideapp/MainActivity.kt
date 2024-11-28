package sheridan.romeroad.usersideapp

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.google.firebase.FirebaseApp
import sheridan.romeroad.usersideapp.ui.common.AppNavigation
import sheridan.romeroad.usersideapp.ui.theme.UserSideAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        // Request permissions
        requestNecessaryPermissions()

        enableEdgeToEdge()
        setContent {
            UserSideAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
    private fun requestNecessaryPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE
        )

        val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsResult ->
            permissionsResult.forEach { (permission, isGranted) ->
                if (!isGranted) {
                    Toast.makeText(
                        this,
                        "Permission $permission is required for proper app functioning.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PermissionChecker.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            requestPermissionsLauncher.launch(missingPermissions.toTypedArray())
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UserSideAppTheme {
        Greeting("Android")
    }
}