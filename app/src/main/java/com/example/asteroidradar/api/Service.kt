package com.example.asteroidradar.api

import com.example.asteroidradar.ImageOfTheDay
import com.example.asteroidradar.util.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface AsteroidService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date") start: String? = null,
        @Query("end_date") end: String? = null,
        @Query("api_key") key: String
    ): String

    @GET("planetary/apod")
    suspend fun getImageOfTheDay(@Query("api_key") key: String): ImageOfTheDay
}

object NasaApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitService = retrofit.create(AsteroidService::class.java)
}
