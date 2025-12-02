package com.example.pictureapi.API

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GiphResponse(
    @SerialName("data")
    val data: List<GifData>
)

@Serializable
data class GifData(
    @SerialName("id")
    val id: String,

    @SerialName("images")
    val images: Images,
)

@Serializable
data class Images(
    @SerialName("original")
    val original: ImageData,

    @SerialName("fixed_height")
    val fixedHeight: ImageData,

    @SerialName("fixed_width")
    val fixedWidth: ImageData
)

@Serializable
data class ImageData(
    @SerialName("url")
    val url: String,

    @SerialName("width")
    val width: String,

    @SerialName("height")
    val height: String
)