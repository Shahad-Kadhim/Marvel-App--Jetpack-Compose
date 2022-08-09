package com.shahad.app.data.remote.response

import com.google.gson.annotations.SerializedName

data class SeriesDto(
    @SerializedName("characters")
    val characters: Details? = null,
    @SerializedName("comics")
    val comics: Details? = null,
    @SerializedName("creators")
    val creators: Creators? = null,
    @SerializedName("description")
    val description: Any? = null,
    @SerializedName("endYear")
    val endYear: Int? = null,
    @SerializedName("events")
    val events: Details? = null,
    @SerializedName("id")
    val id: Long,
    @SerializedName("modified")
    val modified: String,
    @SerializedName("next")
    val next: Any? = null,
    @SerializedName("previous")
    val previous: Any? = null,
    @SerializedName("rating")
    val rating: String? = null,
    @SerializedName("resourceURI")
    val resourceURI: String? = null,
    @SerializedName("startYear")
    val startYear: Int? = null,
    @SerializedName("stories")
    val stories: Stories? = null,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail? = null,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("urls")
    val urls: List<Url>? = null
)