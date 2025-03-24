package sheridan.romeroad.usersideapp.ui.videocall

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import sheridan.romeroad.usersideapp.viewmodels.VideoCallViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoCallScreen(viewModel: VideoCallViewModel = viewModel()) {
    val context = LocalContext.current

    // UI State for toggles
    var isMuted by remember { mutableStateOf(false) }
    var isCameraEnabled by remember { mutableStateOf(true) }
    var isCallActive by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.initWebRTC(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Video Call") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { isMuted = !isMuted }) {
                    Icon(
                        imageVector = if (isMuted) Icons.Filled.Lock else Icons.Filled.Phone,
                        contentDescription = "Mute/Unmute"
                    )
                }

                IconButton(onClick = { isCameraEnabled = !isCameraEnabled }) {
                    Icon(
                        imageVector = if (isCameraEnabled) Icons.Filled.Check else Icons.Filled.Close,
                        contentDescription = "Enable/Disable Camera"
                    )
                }

                Button(
                    onClick = {
                        if (isCallActive) {
                            viewModel.endCall()
                        } else {
                            viewModel.startCall()
                        }
                        isCallActive = !isCallActive
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (isCallActive) "End Call" else "Start Call")
                }
            }
        }
    }
}