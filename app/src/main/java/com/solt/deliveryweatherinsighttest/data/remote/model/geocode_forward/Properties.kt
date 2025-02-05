package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Properties(
    @SerialName("address_line1")
    val addressLine1: String? ="",
    @SerialName("address_line2")
    val addressLine2: String?="",
    @SerialName("category")
    val category: String?="",
    @SerialName("city")
    val city: String?="",
    @SerialName("country")
    val country: String?="",
    @SerialName("country_code")
    val countryCode: String?="",
    @SerialName("county")
    val county: String?="",
    @SerialName("datasource")
    val datasource: Datasource? =Datasource(),
    @SerialName("department_COG")
    val departmentCOG: String? ="",
    @SerialName("formatted")
    val formatted: String?="",
    @SerialName("lat")
    val lat: Double?=0.0,
    @SerialName("lon")
    val lon: Double?=0.0,
    @SerialName("municipality")
    val municipality: String?="",
    @SerialName("place_id")
    val placeId: String?="",
    @SerialName("plus_code")
    val plusCode: String?="",
    @SerialName("plus_code_short")
    val plusCodeShort: String?="",
    @SerialName("postcode")
    val postcode: String?="",
    @SerialName("rank")
    val rank: Rank?=Rank(),
    @SerialName("region")
    val region: String?="",
    @SerialName("result_type")
    val resultType: String?="",
    @SerialName("state")
    val state: String?="",
    @SerialName("state_COG")
    val stateCOG: String?="",
    @SerialName("state_code")
    val stateCode: String?="",
    @SerialName("state_district")
    val stateDistrict: String?="",
    @SerialName("timezone")
    val timezone: Timezone?=Timezone(),
    @SerialName("village")
    val village: String?=""
)