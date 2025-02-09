package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Query(
    @SerialName("parsed")
    val parsed: Parsed? =Parsed(),
    @SerialName("text")
    val text: String?=""
)