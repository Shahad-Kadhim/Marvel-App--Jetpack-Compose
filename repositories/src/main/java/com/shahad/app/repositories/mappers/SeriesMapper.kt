package com.shahad.app.repositories.mappers

import com.shahad.app.core.Mapper
import com.shahad.app.data.local.entities.SeriesEntity
import com.shahad.app.core.models.Series
import javax.inject.Inject

class SeriesMapper @Inject constructor(): Mapper<SeriesEntity, Series> {

    override fun map(input: SeriesEntity): Series =
        Series(
            input.id,
            input.rating,
            input.title,
            input.imageUrl,
            isFavourite = input.isFavorite
        )

    fun  inverseMap(input: Series): SeriesEntity =
        SeriesEntity(
            id = input.id,
            rating = input.rating,
            title = input.title,
            imageUrl = input.imageUrl,
            isFavorite = input.isFavourite,
            lastDateModify = ""
        )
}