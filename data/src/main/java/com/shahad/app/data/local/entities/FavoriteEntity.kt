package com.shahad.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity (
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String
)