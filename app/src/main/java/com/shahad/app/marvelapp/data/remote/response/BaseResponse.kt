package com.shahad.app.marvelapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("attributionHTML")
    val attributionHTML: String? = null,
    @SerializedName("attributionText")
    val attributionText: String? = null,
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("copyright")
    val copyright: String? = null,
    @SerializedName("data")
    val data: Data<T>? = null,
    @SerializedName("etag")
    val etag: String? = null,
    @SerializedName("status")
    val status: String? = null
)