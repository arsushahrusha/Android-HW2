package com.example.pictureapi.data

import com.example.pictureapi.API.GiphApi
import com.example.pictureapi.API.GiphResponse

interface PhotosRepository {
    suspend fun getGiphPhotos(): GiphResponse
}

class NetworkMarsPhotosRepository(
    private val giphApiService: GiphApi
): PhotosRepository {
    override suspend fun getGiphPhotos(): GiphResponse = giphApiService.getPhotos()
}