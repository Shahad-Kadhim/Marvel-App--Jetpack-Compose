package com.shahad.app.marvelapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class StoryItem(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("resourceURI")
    val resourceURI: String? = null,
    @SerializedName("type")
    val type: String? = null
)