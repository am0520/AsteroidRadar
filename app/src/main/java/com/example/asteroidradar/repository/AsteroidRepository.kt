package com.example.asteroidradar.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.ImageOfTheDay
import com.example.asteroidradar.api.NasaApi
import com.example.asteroidradar.api.asDatabaseModel
import com.example.asteroidradar.api.asDomainModel
import com.example.asteroidradar.api.parseAsteroidsJsonResult
import com.example.asteroidradar.database.AsteroidDatabase
import com.example.asteroidradar.util.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

private const val TAG = "AsteroidRepository"

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    val imageOfTheDay: LiveData<ImageOfTheDay?> =
        Transformations.map(database.asteroidDao.getImageOfTheDay()) {
            it?.let {
                val image = if (it.mediaType == ImageOfTheDay.MEDIA_TYPE_IMAGE) it.asDomainModel()
                else null
                image
            }
        }

    suspend fun refreshData(startDate: String? = null, endDate: String? = null) {
        withContext(Dispatchers.IO) {
            try {
                val result = NasaApi.retrofitService.getAsteroids(startDate, endDate, API_KEY)
                val asteroidList = parseAsteroidsJsonResult(JSONObject(result))
                // To update asteroids for specific dates and delete invalid date asteroids.
                if(endDate!=null)
                    database.asteroidDao.clear()
                database.asteroidDao.insertAsteroids(*asteroidList.asDatabaseModel())
            } catch (_: Exception) {
            }

            try {
                val imageOfTheDay: ImageOfTheDay = NasaApi.retrofitService.getImageOfTheDay(API_KEY)
                database.asteroidDao.insertImageOfTheDay(imageOfTheDay.asDatabaseModel())
            } catch (_: Exception) {
            }
        }
    }
}
