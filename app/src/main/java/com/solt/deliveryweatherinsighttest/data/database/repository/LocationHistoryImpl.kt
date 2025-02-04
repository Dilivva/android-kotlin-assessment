package com.solt.deliveryweatherinsighttest.data.database.repository

import androidx.paging.PagingSource
import com.solt.deliveryweatherinsighttest.data.database.dao.LocationHistoryDAO
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationHistoryImpl @Inject constructor( val dao: LocationHistoryDAO) :
    LocationHistoryRepository {
    override fun getLocationHistories(): PagingSource<Int, LocationHistoryEntity> {
        return  dao.getLocationHistory()
    }

    override  fun getLast5LocationHistories(): Flow<List<LocationHistoryEntity>> {
       return dao.getLastFiveLocationHistoryEntries()

    }

    override suspend fun insertLocationHistory(locationHistory: LocationHistoryEntity) {
        return withContext(Dispatchers.IO){
            dao.insertLocationHistory(locationHistory)
        }
    }

    override suspend fun deleteLocationHistory(locationHistory: LocationHistoryEntity) {
        return withContext(Dispatchers.IO){
            dao.deleteLocationHistory(locationHistory)
        }
    }

}