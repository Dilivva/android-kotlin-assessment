package com.solt.deliveryweatherinsighttest.data.remote.repository

import androidx.paging.PagingSource
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity

interface LocationHistoryRepository {
    fun getLocationHistories():PagingSource<Int,LocationHistoryEntity>
     suspend fun getLast5LocationHistories():List<LocationHistoryEntity>

     suspend fun insertLocationHistory(locationHistory: LocationHistoryEntity)

     suspend fun deleteLocationHistory(locationHistory: LocationHistoryEntity)
}