package com.shahad.app.data.mappers

import com.shahad.app.data.Mapper
import com.shahad.app.data.local.entities.SeriesEntity
import com.shahad.app.data.remote.response.SeriesDto
import com.shahad.app.data.toImageUrl
import javax.inject.Inject

class SeriesEntityMapper @Inject constructor(): Mapper<SeriesDto, SeriesEntity> {

    override fun map(input: SeriesDto): SeriesEntity =
        SeriesEntity(
            id = input.id,
            rating = input.rating,
            title = input.title ?: "",
            imageUrl = input.thumbnail.toImageUrl(),
            lastDateModify = input.modified,
            isFavorite = false
        )

}