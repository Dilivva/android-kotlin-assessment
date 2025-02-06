package com.solt.deliveryweatherinsighttest.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationHistoryDAO {
    //The basic operations should be
    //Getting of location History,
    //Inserting
    //Deleting

    //Getting of location History
    //It will be paged
    //Get the location history by time from top to bottom
    @Query("SELECT * FROM LocationHistoryEntity ORDER BY timeStamp DESC")
    fun getLocationHistory(): PagingSource<Int,LocationHistoryEntity>

    //Get the last five location History entries
    @Query("SELECT * FROM LocationHistoryEntity ORDER BY timeStamp DESC LIMIT 5 ")
 fun getLastFiveLocationHistoryEntries(): Flow<List<LocationHistoryEntity>>

    //Insertion
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocationHistory(locationHistory:LocationHistoryEntity)

    //Deletion
    @Delete
    suspend fun deleteLocationHistory(locationHistory: LocationHistoryEntity)

}