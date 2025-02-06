package com.solt.deliveryweatherinsighttest.data.database.repository

import com.solt.deliveryweatherinsighttest.data.database.dao.StationDAO
import com.solt.deliveryweatherinsighttest.data.database.model.StationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(val stationDao:StationDAO):StationRepository {
    override fun getStationsAsFlow(): Flow<List<StationEntity>> {
        return stationDao.getStationsAsFlow()
    }

    override suspend fun getStations(): List<StationEntity>{
        return stationDao.getStations()
    }

    override suspend fun insertStation(station: StationEntity) {
        stationDao.insertStationEntity(station)
    }

    override suspend fun deleteStation(station: StationEntity) {
        stationDao.deleteStationEntity(station)
    }
}