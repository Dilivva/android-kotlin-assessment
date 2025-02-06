package com.solt.deliveryweatherinsighttest.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solt.deliveryweatherinsighttest.data.database.dao.StationDAO
import com.solt.deliveryweatherinsighttest.data.database.model.StationEntity
import com.solt.deliveryweatherinsighttest.data.database.model.StationTypeConverter

@Database([StationEntity::class], version = 1)
@TypeConverters(StationTypeConverter::class)
 abstract class StationDatabase():RoomDatabase() {
     abstract fun stationDao() :StationDAO
}