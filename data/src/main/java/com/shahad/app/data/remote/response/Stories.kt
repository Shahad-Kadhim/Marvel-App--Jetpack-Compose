package com.shahad.app.data.remote.response
import com.google.gson.annotations.SerializedName

data class Stories(
    @SerializedName("available")
    val available: Int? = null,
    @SerializedName("collectionURI")
    val collectionURI: String? = null,
    @SerializedName("items")
    val items: List<StoryItem>? = null,
    @SerializedName("returned")
    val returned: Int? = null
)