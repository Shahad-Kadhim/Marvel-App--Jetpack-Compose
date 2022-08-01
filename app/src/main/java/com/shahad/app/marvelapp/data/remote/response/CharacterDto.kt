package com.shahad.app.marvelapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("comics")
    val comics: Details? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("events")
    val events: Details? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String? = null,
    @SerializedName("series")
    val series: Details? = null,
    @SerializedName("stories")
    val stories: Stories? = null,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail? = null,
    @SerializedName("urls")
    val urls: List<Url>? = null
)
