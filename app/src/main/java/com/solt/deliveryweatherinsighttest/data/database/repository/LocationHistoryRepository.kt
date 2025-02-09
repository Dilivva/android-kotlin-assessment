package com.solt.deliveryweatherinsighttest.data.database.repository

import androidx.paging.PagingSource
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity
import kotlinx.coroutines.flow.Flow

interface LocationHistoryRepository {
    fun getLocationHistories():PagingSource<Int,LocationHistoryEntity>
      fun getLast5LocationHistories(): Flow<List<LocationHistoryEntity>>

     suspend fun insertLocationHistory(locationHistory: LocationHistoryEntity)

     suspend fun deleteLocationHistory(locationHistory: LocationHistoryEntity)
}