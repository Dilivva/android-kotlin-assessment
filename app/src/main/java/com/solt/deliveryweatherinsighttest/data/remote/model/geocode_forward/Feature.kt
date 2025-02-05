package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    @SerialName("bbox")
    val bbox: List<Double?>? = emptyList(),
    @SerialName("geometry")
    val geometry: Geometry?=Geometry(),
    @SerialName("properties")
    val properties: Properties?=Properties(),
    @SerialName("type")
    val type: String?=""
)