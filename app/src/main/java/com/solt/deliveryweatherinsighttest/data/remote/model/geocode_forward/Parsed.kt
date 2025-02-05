package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Parsed(
    @SerialName("city")
    val city: String?="",
    @SerialName("expected_type")
    val expectedType: String?=""
)