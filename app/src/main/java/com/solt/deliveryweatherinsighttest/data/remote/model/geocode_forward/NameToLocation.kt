package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NameToLocation(
    @SerialName("features")
    val features: List<Feature?>? = emptyList(),
    @SerialName("query")
    val query: Query?=Query(),
    @SerialName("type")
    val type: String?=""
)