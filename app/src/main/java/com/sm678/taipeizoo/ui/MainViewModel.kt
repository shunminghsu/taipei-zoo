package com.sm678.taipeizoo.ui

import android.app.Application
import androidx.lifecycle.*
import com.sm678.taipeizoo.model.Area
import com.sm678.taipeizoo.model.Plant

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var clickedItem: Area? = null
    var clickedPlant: Plant? = null

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}