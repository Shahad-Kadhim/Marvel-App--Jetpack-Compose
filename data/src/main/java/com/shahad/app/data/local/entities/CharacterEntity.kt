package com.shahad.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val imageUrl: String,
    val description: String?,
    val modified: String
)
