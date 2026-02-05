package com.example.pictureapi.API

import retrofit2.http.GET
import retrofit2.http.Query

interface GiphApi {
    @GET("v1/gifs/search")
    suspend fun getPhotos(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("q") q: String = "cat",

        ): GiphResponse
}
