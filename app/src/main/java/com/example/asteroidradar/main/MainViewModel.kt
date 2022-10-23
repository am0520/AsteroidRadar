package com.example.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.api.dateNextWeek
import com.example.asteroidradar.api.dateToday
import com.example.asteroidradar.api.dateTomorrow
import com.example.asteroidradar.database.getDatabase
import com.example.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    init {
        viewModelScope.launch(Dispatchers.IO) { asteroidRepository.refreshData() }
    }

    val asteroids = asteroidRepository.asteroids

    val imageOfTheDay = asteroidRepository.imageOfTheDay

    private val _navigateToDetail = MutableLiveData<Asteroid?>()
    val navigateToDetail: LiveData<Asteroid?>
        get() = _navigateToDetail

    fun onItemClick(selectedAsteroid: Asteroid) {
        _navigateToDetail.value = selectedAsteroid
    }

    fun doneNavigating() {
        _navigateToDetail.value = null
    }

    fun showDay() {
        viewModelScope.launch(Dispatchers.IO) {
            asteroidRepository.refreshData(
                dateToday(), dateTomorrow()
            )
        }
    }

    fun showWeek() {
        viewModelScope.launch(Dispatchers.IO) {
            asteroidRepository.refreshData(
                dateToday(), dateNextWeek()
            )
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
