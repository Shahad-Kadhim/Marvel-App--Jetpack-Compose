package com.shahad.app.marvelapp.data.local.entities

import androidx.room.*

@Entity
data class CreatorEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val lastModify: String
)
