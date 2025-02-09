package com.solt.deliveryweatherinsighttest.data.remote.repository

import com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward.NameToLocationDisplayModel
import com.solt.deliveryweatherinsighttest.data.remote.model.util.OperationResult

interface GeocodingRepository {
    suspend fun searchForLocationByName(  name:String):OperationResult
     suspend fun getNameByLatLng(latitude:Double,longitude:Double):OperationResult
}