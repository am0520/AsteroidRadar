package com.example.asteroidradar.database

import androidx.room.*

@Entity
data class DatabaseAsteroid(
    @PrimaryKey
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

@Entity
data class DatabaseImageOfTheDay(
    @PrimaryKey
    val url: String,
    val mediaType: String,
    val title: String
)
