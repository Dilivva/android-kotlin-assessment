package com.solt.deliveryweatherinsighttest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity
import com.solt.deliveryweatherinsighttest.data.database.repository.LocationHistoryRepository
import com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward.NameToLocationDisplayModel
import com.solt.deliveryweatherinsighttest.data.remote.model.util.OperationResult
import com.solt.deliveryweatherinsighttest.data.remote.repository.GeocodingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(val locationHistoryRepository: LocationHistoryRepository,val geocodingRepository: GeocodingRepository): ViewModel() {
    fun getLastFiveLocations():Flow<List<LocationHistoryEntity>> =
        locationHistoryRepository.getLast5LocationHistories()
    fun getLocationHistory(): Flow<PagingData<LocationHistoryEntity>> {
        return Pager(PagingConfig(10)){locationHistoryRepository.getLocationHistories()}.flow.cachedIn(viewModelScope)
    }

    fun deleteLocationHistoryEntity(locationHistoryEntity: LocationHistoryEntity){
        viewModelScope.launch {
            locationHistoryRepository.deleteLocationHistory(locationHistoryEntity)
        }
    }
    suspend fun getLocationNameByLatLng(latitude: Double,longitude: Double): NameToLocationDisplayModel?{
        val result = geocodingRepository.getNameByLatLng(latitude,longitude)
        return when(result){
            is OperationResult.Failure -> {
                Log.i("Search ","${result.e.message}")
                null
            }
            is OperationResult.Success<*> -> result.data as NameToLocationDisplayModel
        }
    }

}