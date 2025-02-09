package com.solt.deliveryweatherinsighttest.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solt.deliveryweatherinsighttest.data.database.dao.LocationHistoryDAO
import com.solt.deliveryweatherinsighttest.data.database.model.DateConverter
import com.solt.deliveryweatherinsighttest.data.database.model.LocationHistoryEntity

@Database([LocationHistoryEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class LocationHistoryDatabase: RoomDatabase() {
  abstract fun locationHistoryDAO():LocationHistoryDAO

}