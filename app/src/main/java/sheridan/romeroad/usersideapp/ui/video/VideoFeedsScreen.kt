package sheridan.romeroad.usersideapp.ui.video

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VideoFeedsScreen() {
    val context = LocalContext.current
    var camera1Status by remember { mutableStateOf("Loading...") }
    var camera2Status by remember { mutableStateOf("Loading...") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Video Feeds", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            VideoStreamCard(
                title = "Camera 1",
                status = camera1Status,
                rtspUrl = "rtsp://192.168.0.101/stream1",
                onStatusUpdate = { status -> camera1Status = status },
                context = context
            )
            VideoStreamCard(
                title = "Camera 2",
                status = camera2Status,
                rtspUrl = "rtsp://192.168.0.102/stream2",
                onStatusUpdate = { status -> camera2Status = status },
                context = context
            )
        }
    }
}

@Composable
fun VideoStreamCard(
    title: String,
    status: String,
    rtspUrl: String,
    onStatusUpdate: (String) -> Unit,
    context: Context
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            Text("Status: $status", style = TextStyle(fontSize = 16.sp, color = Color.Gray))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                VideoPlayer(rtspUrl = rtspUrl, onStatusUpdate = onStatusUpdate, context = context)
            }
        }
    }
}

@Composable
fun VideoPlayer(rtspUrl: String, onStatusUpdate: (String) -> Unit, context: Context) {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(rtspUrl)))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(exoPlayer) {
        onStatusUpdate("Playing...")
        onDispose {
            exoPlayer.release()
            onStatusUpdate("Stopped")
        }
    }

    AndroidView(
        factory = { PlayerView(it).apply { player = exoPlayer } },
        modifier = Modifier.fillMaxSize()
    )
}
