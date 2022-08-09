package com.shahad.app.core.models

data class Series (
    val id: Long,
    val rating: String? = null,
    val title: String,
    val imageUrl: String,
    var isFavourite: Boolean = false
)