package com.solt.deliveryweatherinsighttest.data.remote.dao

import com.solt.deliveryweatherinsighttest.data.remote.Utils
import com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward.NameToLocation
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface GeoCodeApiDAO {
    @GET()
   suspend fun getLatLngByLocationName(@Url url:String, @Query("text") searchQuery:String, @Query ("apiKey")key:String): NameToLocation
}