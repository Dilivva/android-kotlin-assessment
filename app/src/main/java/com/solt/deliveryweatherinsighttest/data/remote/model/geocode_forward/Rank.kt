package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rank(
    @SerialName("confidence")
    val confidence: Int? =0,
    @SerialName("confidence_city_level")
    val confidenceCityLevel: Int? =0,
    @SerialName("importance")
    val importance: Double? =0.0,
    @SerialName("match_type")
    val matchType: String? ="",
    @SerialName("popularity")
    val popularity: Double?=0.0
)