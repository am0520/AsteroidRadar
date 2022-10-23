package com.example.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asteroidradar.ImageOfTheDay

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM databaseasteroid")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM databaseimageoftheday")
    fun getImageOfTheDay(): LiveData<DatabaseImageOfTheDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(vararg asteroid: DatabaseAsteroid)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageOfTheDay(imageOfTheDay: DatabaseImageOfTheDay)

    @Query("DELETE FROM databaseasteroid")
    suspend fun clear()
}

@Database(
    entities = [DatabaseAsteroid::class, DatabaseImageOfTheDay::class],
    version = 1,
    exportSchema = false
)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val asteroidDao: AsteroidDao
}

@Volatile
private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroid_database"
            ).build()
        }
    }
    return INSTANCE
}
