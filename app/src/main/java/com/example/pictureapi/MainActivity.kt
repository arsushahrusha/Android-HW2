package com.example.pictureapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pictureapi.ui.screens.HomeScreen
import com.example.pictureapi.ui.screens.PhotoViewModel
import com.example.pictureapi.ui.theme.PictureApiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PictureApiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val photoViewModel: PhotoViewModel = viewModel(factory = PhotoViewModel.Factory)
                    HomeScreen(
                        photoUiState = photoViewModel.photoUiState,
                        retryAction = photoViewModel::getGiphPhotos,
                    )
                }
            }
        }
    }
}
