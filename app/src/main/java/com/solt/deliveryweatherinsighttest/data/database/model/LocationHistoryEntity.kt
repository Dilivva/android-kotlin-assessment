package com.solt.deliveryweatherinsighttest.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

//This is an entity for the location history table
//We will need a name field for the location
//There are two ways
//1. We can get the name of the location by using a Geocoding api and then insert it b4 storing it into the table
// 2. We just store it wit only longitude and latitude
@Entity
 data class LocationHistoryEntity(
    @PrimaryKey(true)
    val id:Int,
    val longitude:Double,
    val latitude:Double,
    //Find out how to do timestamp in room
    val timeStamp: Date

             )
