package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward

data class NameToLocationDisplayModel(
    val listOfSearchResults: List<GeoCodedLocation>
){

}
fun NameToLocation.toModel():NameToLocationDisplayModel{
    val listOfGeoCodedLocations = this.features?.map {
        GeoCodedLocation(it?.properties?.formatted?:"Unknown",it?.properties?.lat,it?.properties?.lon)
    }?: emptyList()
    return NameToLocationDisplayModel(listOfGeoCodedLocations)
}
data class GeoCodedLocation(val name:String?,val latitude:Double?,val longitude:Double?)
