package com.example.asteroidradar.api

import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.ImageOfTheDay
import com.example.asteroidradar.database.DatabaseAsteroid
import com.example.asteroidradar.database.DatabaseImageOfTheDay

fun List<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {

    return map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {

    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun ImageOfTheDay.asDatabaseModel(): DatabaseImageOfTheDay {

    return DatabaseImageOfTheDay(
        url = this.url,
        mediaType = this.mediaType,
        title = this.title
    )
}

fun DatabaseImageOfTheDay.asDomainModel(): ImageOfTheDay {

    return ImageOfTheDay(
        url = this.url,
        mediaType = this.mediaType,
        title = this.title
    )
}
