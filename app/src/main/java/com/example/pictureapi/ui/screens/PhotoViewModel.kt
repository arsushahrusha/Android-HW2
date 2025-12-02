package com.example.pictureapi.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pictureapi.API.GiphResponse
import com.example.pictureapi.PhotosApplication
import com.example.pictureapi.data.PhotosRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

//Закрытый интерфейс
sealed interface PhotoUiState {
    data class Success(val photos: GiphResponse) : PhotoUiState
    object Error : PhotoUiState
    object Loading : PhotoUiState
}

class PhotoViewModel(private val photosRepository: PhotosRepository) : ViewModel() {
    var photoUiState: PhotoUiState by mutableStateOf(PhotoUiState.Loading)
        private set

    init {
        getGiphPhotos()
    }

    fun getGiphPhotos() {
        viewModelScope.launch {
            photoUiState = PhotoUiState.Loading
            photoUiState = try {
                val result = photosRepository.getGiphPhotos()
                PhotoUiState.Success(result)
            } catch (e: IOException) {
                PhotoUiState.Error
            } catch (e: HttpException) {
                PhotoUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PhotosApplication)
                val photosRepository = application.container.photosRepository
                PhotoViewModel(photosRepository = photosRepository)
            }
        }
    }
}