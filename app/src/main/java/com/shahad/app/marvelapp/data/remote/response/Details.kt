package com.shahad.app.marvelapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class Details(
    @SerializedName("available")
    val available: Int? = null,
    @SerializedName("collectionURI")
    val collectionURI: String? = null,
    @SerializedName("items")
    val items: List<Summary>? = null,
    @SerializedName("returned")
    val returned: Int? = null
)