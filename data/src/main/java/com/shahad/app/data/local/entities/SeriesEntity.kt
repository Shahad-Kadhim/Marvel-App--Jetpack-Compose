package com.shahad.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SeriesEntity (
    @PrimaryKey val id: Int,
    val rating: String?,
    val title: String,
    val imageUrl: String,
    val lastDateModify: String
)