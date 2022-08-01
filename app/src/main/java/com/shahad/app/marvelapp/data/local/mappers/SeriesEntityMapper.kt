package com.shahad.app.marvelapp.data.local.mappers

import com.shahad.app.marvelapp.data.local.entities.SeriesEntity
import com.shahad.app.marvelapp.data.remote.response.SeriesDto
import com.shahad.app.marvelapp.domain.mappers.Mapper
import com.shahad.app.marvelapp.util.toImageUrl
import javax.inject.Inject

class SeriesEntityMapper @Inject constructor(): Mapper<SeriesDto, SeriesEntity> {

    override fun map(input: SeriesDto): SeriesEntity =
        SeriesEntity(
            id = input.id,
            rating = input.rating,
            title = input.title,
            imageUrl = input.thumbnail.toImageUrl(),
            lastDateModify = input.modified
        )

}