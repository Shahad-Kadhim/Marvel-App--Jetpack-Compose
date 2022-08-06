package com.shahad.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class Data<T>(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("limit")
    val limit: Int? = null,
    @SerializedName("offset")
    val offset: Int? = null,
    @SerializedName("results")
    val results: List<T>? = null,
    @SerializedName("total")
    val total: Int? = null
)
