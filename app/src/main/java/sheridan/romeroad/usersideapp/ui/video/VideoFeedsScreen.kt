package sheridan.romeroad.usersideapp.ui.video

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 **/
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VideoFeedsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Video Feeds") },
                //backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Video Feeds Coming Soon!",
                style = MaterialTheme.typography.bodyLarge,
                //color = MaterialTheme.colors.onBackground
            )
        }
    }
}
