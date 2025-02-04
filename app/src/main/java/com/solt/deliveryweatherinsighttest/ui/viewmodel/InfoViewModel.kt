package com.solt.deliveryweatherinsighttest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity
import com.solt.deliveryweatherinsighttest.data.database.repository.LocationHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel@Inject constructor(val locationHistoryRepository: LocationHistoryRepository): ViewModel() {
    suspend fun getLastFiveLocations():Flow<List<LocationHistoryEntity>> =
        locationHistoryRepository.getLast5LocationHistories()
    fun getLocationHistory(): Flow<PagingData<LocationHistoryEntity>> {
        return Pager(PagingConfig(20)){locationHistoryRepository.getLocationHistories()}.flow.cachedIn(viewModelScope)
    }
    fun insertLocationHistory(locationHistoryEntity: LocationHistoryEntity){
        viewModelScope.launch {
            locationHistoryRepository.insertLocationHistory(locationHistoryEntity)
        }
    }
    fun deleteLocationHistoryEntity(locationHistoryEntity: LocationHistoryEntity){
        viewModelScope.launch {
            locationHistoryRepository.deleteLocationHistory(locationHistoryEntity)
        }
    }

}