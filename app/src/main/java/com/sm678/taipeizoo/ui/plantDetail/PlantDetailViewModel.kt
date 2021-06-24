package com.sm678.taipeizoo.ui.plantDetail

import android.app.Application
import androidx.lifecycle.*

class PlantDetailViewModel (application: Application) : AndroidViewModel(application) {


    /**
     * Factory for constructing ViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlantDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PlantDetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}