package com.shahad.app.data.remote.response


import com.google.gson.annotations.SerializedName

data class Summary(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("resourceURI")
    val resourceURI: String? = null
)