package com.solt.deliveryweatherinsighttest.data.database.repository

import com.solt.deliveryweatherinsighttest.data.database.model.StationEntity
import kotlinx.coroutines.flow.Flow

interface StationRepository {
    fun getStationsAsFlow(): Flow<List<StationEntity>>
    suspend fun getStations():List<StationEntity>
    suspend fun insertStation(station: StationEntity)
    suspend fun deleteStation(station: StationEntity)


}