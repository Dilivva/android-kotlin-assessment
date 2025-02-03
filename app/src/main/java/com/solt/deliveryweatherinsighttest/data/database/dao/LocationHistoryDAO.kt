package com.solt.deliveryweatherinsighttest.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity

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
    fun getLocationHistory():List<LocationHistoryEntity>
}