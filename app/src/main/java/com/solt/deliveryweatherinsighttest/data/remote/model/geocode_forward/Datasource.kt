package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Datasource(
    @SerialName("attribution")
    val attribution: String?="",
    @SerialName("license")
    val license: String?="",
    @SerialName("sourcename")
    val sourcename: String?="",
    @SerialName("url")
    val url: String?=""
)