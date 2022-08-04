package com.shahad.app.marvelapp.domain.models

data class Series (
    val id: Int,
    val rating: String? = null,
    val title: String,
    val imageUrl: String,
)