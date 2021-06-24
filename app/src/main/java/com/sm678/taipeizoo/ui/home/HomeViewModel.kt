package com.sm678.taipeizoo.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.sm678.taipeizoo.model.Area
import com.sm678.taipeizoo.network.TaipeiZooApi


import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    ////Navigation codes :
    private val _navigateToDetail = MutableLiveData<Boolean?>()
    val navigateToDetail: LiveData<Boolean?>
        get() = _navigateToDetail

    fun doneNavigateToDetail() {
        _navigateToDetail.value = null
    }

    fun onNavigateToDetail() {
        _navigateToDetail.value = true
    }
    ////End of Navigation codes


    var isLoading = MutableLiveData(true)
    private val _listItem = MutableLiveData<List<Area>>()
    val listItem: LiveData<List<Area>>
        get() = _listItem


    init {
        getListItem()
    }

    private fun getListItem() {
        viewModelScope.launch {
            try {
                val result = TaipeiZooApi.retrofitService.getZooArea()
                _listItem.value = result.result.results
            } catch (e: Exception) {
                _listItem.value = ArrayList()
            }
            isLoading.postValue(false)
        }
    }

    /**
     * Factory for constructing ViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}