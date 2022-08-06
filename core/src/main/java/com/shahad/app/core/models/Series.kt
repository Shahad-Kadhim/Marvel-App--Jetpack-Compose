package com.shahad.app.core.models

data class Series (
    val id: Int,
    val rating: String? = null,
    val title: String,
    val imageUrl: String,
    var isFavourite: Boolean = false
)