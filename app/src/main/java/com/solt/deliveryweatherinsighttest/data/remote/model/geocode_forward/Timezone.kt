package com.solt.deliveryweatherinsighttest.data.remote.model.geocode_forward


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Timezone(
    @SerialName("abbreviation_DST")
    val abbreviationDST: String? ="",
    @SerialName("abbreviation_STD")
    val abbreviationSTD: String? ="",
    @SerialName("name")
    val name: String? ="",
    @SerialName("offset_DST")
    val offsetDST: String? ="",
    @SerialName("offset_DST_seconds")
    val offsetDSTSeconds: Int? =0,
    @SerialName("offset_STD")
    val offsetSTD: String? ="",
    @SerialName("offset_STD_seconds")
    val offsetSTDSeconds: Int? =0
)