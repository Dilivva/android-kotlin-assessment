package com.solt.deliveryweatherinsighttest.data.database.model

import androidx.room.Entity
import androidx.room.TypeConverter

//A user can mark a location to be delivery station or pickup station
//That station will be stored and retrieved for the user
@Entity(primaryKeys = ["longitude","latitude"])
data class StationEntity (
    val longitude:Double,
    val latitude:Double,
    val stationType:StationType
)
enum class StationType{
    DELIVERY, PICKUP
}
class StationTypeConverter(){
    @TypeConverter
    fun fromStationTypeToString(stationType:StationType):String{
        return stationType.name
    }
    @TypeConverter
    fun fromStringToStationType(name:String):StationType{
         return StationType.valueOf(name)
    }

}
