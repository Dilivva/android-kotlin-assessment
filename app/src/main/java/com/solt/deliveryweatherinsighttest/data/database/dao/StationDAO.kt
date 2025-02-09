package com.solt.deliveryweatherinsighttest.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solt.deliveryweatherinsighttest.data.database.model.StationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDAO {

    //This is to get all the stations we can't page it so we use it as a flow
    @Query("SELECT * FROM StationEntity")
   suspend fun getStations():List<StationEntity>
    @Query("SELECT * FROM StationEntity")
    fun getStationsAsFlow(): Flow<List<StationEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend  fun insertStationEntity(station:StationEntity)

    @Delete
   suspend fun deleteStationEntity(station: StationEntity)
}