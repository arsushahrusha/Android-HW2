package com.example.pictureapi.data

import android.content.Context
import retrofit2.Retrofit
import com.example.pictureapi.API.GiphApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import com.example.pictureapi.R

interface AppContainer {
    val photosRepository: PhotosRepository
}

class DefaultAppContainer (
    context: Context
) : AppContainer {

    private val baseUrl = context.getString(R.string.url)

    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: GiphApi by lazy {
        retrofit.create(GiphApi::class.java)
    }

    override val photosRepository: PhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }

}