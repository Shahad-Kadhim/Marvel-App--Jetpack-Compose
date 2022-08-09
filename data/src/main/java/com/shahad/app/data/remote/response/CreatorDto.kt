package com.shahad.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreatorDto(
    @SerializedName("comics")
    val comics: Details? = null,
    @SerializedName("events")
    val events: Details? = null,
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("fullName")
    val name: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("lastName")
    val lastName: String? = null,
    @SerializedName("middleName")
    val middleName: String? = null,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("resourceURI")
    val resourceURI: String? = null,
    @SerializedName("series")
    val series: Details? = null,
    @SerializedName("stories")
    val stories: Stories? = null,
    @SerializedName("suffix")
    val suffix: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail? = null,
    @SerializedName("urls")
    val urls: List<Url>? = null
)