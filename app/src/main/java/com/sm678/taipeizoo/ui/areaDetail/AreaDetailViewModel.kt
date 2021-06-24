package com.sm678.taipeizoo.ui.areaDetail

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.sm678.taipeizoo.model.Area
import com.sm678.taipeizoo.model.AreaHeader
import com.sm678.taipeizoo.model.DataItem
import com.sm678.taipeizoo.model.Plant
import com.sm678.taipeizoo.network.TaipeiZooApi

import kotlinx.coroutines.launch
import me.samlss.broccoli.Broccoli

class AreaDetailViewModel (area: Area, application: Application) : AndroidViewModel(application) {

    ////Navigation codes :
    private val _navigateToPlantDetail = MutableLiveData<Boolean?>()
    val navigateToPlantDetail: LiveData<Boolean?>
        get() = _navigateToPlantDetail

    fun doneNavigateToPlantDetail() {
        _navigateToPlantDetail.value = null
    }

    fun onNavigateToPlantDetail() {
        needInit = false
        _navigateToPlantDetail.value = true
    }
    ////End of Navigation codes


    private val _earlyListItem = MutableLiveData<List<DataItem>>()
    val earlyListItem: LiveData<List<DataItem>>
        get() = _earlyListItem

    private val _listItem = MutableLiveData<List<Plant>>()
    val listItem: LiveData<List<Plant>>
        get() = _listItem

    val broccoliViewMap = mutableMapOf<View, Broccoli>()
    val areaHeader = AreaHeader(area.picUrl, area.info, area.name, area.category, area.url)

    var needInit = true

    init {
        _earlyListItem.value = listOf(areaHeader)
        getListItem()
    }

    private fun getListItem() {
        viewModelScope.launch {
            try {
                val result = TaipeiZooApi.retrofitService.getPlants(where = areaHeader.name)
                _listItem.value = result.result.results
            } catch (e: Exception) {
                _listItem.value = ArrayList()
            }
        }
    }

    /**
     * Factory for constructing ViewModel with parameter
     */
    class Factory(val area: Area, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AreaDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AreaDetailViewModel(area, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}