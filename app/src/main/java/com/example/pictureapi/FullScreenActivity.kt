package com.example.pictureapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.pictureapi.ui.screens.HomeScreen
import com.example.pictureapi.ui.screens.PhotoViewModel
import com.example.pictureapi.ui.screens.cardElevation
import com.example.pictureapi.ui.theme.PictureApiTheme

class FullScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val gifUrl = intent.getStringExtra("url").toString()
        setContent {
            GifFullScreen (
                gifUrl = gifUrl,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun GifFullScreen (
    gifUrl: String,
    modifier: Modifier
) {
    Card (
        modifier = modifier
            .fillMaxSize()

    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(gifUrl)
                .decoderFactory(GifDecoder.Factory())
                //плавный переход между элементами или состояниями
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.errorcircle),
            placeholder = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.gif_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}