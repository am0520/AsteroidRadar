package com.example.asteroidradar

import com.squareup.moshi.Json

data class ImageOfTheDay(
    val url: String,
    @Json(name = "media_type")
    val mediaType: String,
    val title: String
) {
    companion object {
        const val MEDIA_TYPE_IMAGE = "image"
    }
}
