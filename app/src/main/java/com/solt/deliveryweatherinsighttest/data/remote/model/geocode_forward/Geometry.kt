package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    @SerialName("coordinates")
    val coordinates: List<Double?>? = emptyList(),
    @SerialName("type")
    val type: String?=""
)