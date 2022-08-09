package com.shahad.app.data.remote.response


import com.google.gson.annotations.SerializedName

data class StoryDto(
    @SerializedName("characters")
    val characters: Details?,
    @SerializedName("comics")
    val comics: Details? ,
    @SerializedName("creators")
    val creators: Creators? ,
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("events")
    val events: Details? ,
    @SerializedName("id")
    val id: Long,
    @SerializedName("modified")
    val modified: String? = "",
    @SerializedName("originalIssue")
    val originalIssue: Summary? ,
    @SerializedName("resourceURI")
    val resourceURI: String? = "",
    @SerializedName("series")
    val series: Details? ,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?
)