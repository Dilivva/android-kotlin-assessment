package com.solt.deliveryweatherinsighttest.data.remote.repository

import com.solt.deliveryweatherinsighttest.BuildConfig
import com.solt.deliveryweatherinsighttest.data.remote.Utils
import com.solt.deliveryweatherinsighttest.data.remote.dao.GeoCodeApiDAO
import com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward.NameToLocationDisplayModel
import com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward.toModel
import com.solt.deliveryweatherinsighttest.data.remote.model.util.OperationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GeocodingRepositoryImpl @Inject constructor(val geocodingApi:GeoCodeApiDAO):GeocodingRepository {
    override suspend fun searchForLocationByName(name: String): OperationResult {
         return withContext(Dispatchers.IO){
             try {
                 val result = geocodingApi.getLatLngByLocationName(Utils.BASE_FORWARD_GEOCODING_URL,name,BuildConfig.geoApifyKey)
                 val resultAsModel = result.toModel()
                 OperationResult.Success(resultAsModel)
             }catch (e:CancellationException){
                 //Throw it back as the coroutine needs it
                 throw  e
             }catch (e:Exception){
                 OperationResult.Failure(e)
             }
         }
    }

}