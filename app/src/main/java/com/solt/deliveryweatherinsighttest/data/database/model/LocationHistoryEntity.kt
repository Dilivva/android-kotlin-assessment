package com.solt.deliveryweatherinsighttest.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.util.Date

//This is an entity for the location history table
//We will need a name field for the location
//There are two ways
//1. We can get the name of the location by using a Geocoding api and then insert it b4 storing it into the table
// 2. We just store it wit only longitude and latitude
@Entity(primaryKeys = ["longitude", "latitude"])
 data class LocationHistoryEntity(
    //The primaryKeys will be the longitude and latitude
    val longitude:Double,
    val latitude:Double,
    //We need a type converter
    val timeStamp: Date

 )
class DateConverter{
    @TypeConverter
    fun fromDateToLong(date :Date):Long{
       return  date.time
    }
    @TypeConverter
    fun fromLongToDate(time:Long):Date{
        return  Date(time)
    }


}
