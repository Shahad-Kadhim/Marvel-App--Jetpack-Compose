package com.shahad.app.data.local.entities

import androidx.room.*

@Entity
data class CreatorEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val imageUrl: String,
    val lastModify: String
)
