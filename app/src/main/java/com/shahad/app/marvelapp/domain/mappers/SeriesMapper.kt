package com.shahad.app.marvelapp.domain.mappers

import com.shahad.app.marvelapp.data.local.entities.SeriesEntity
import com.shahad.app.marvelapp.domain.models.Series
import javax.inject.Inject

class SeriesMapper @Inject constructor(): Mapper<SeriesEntity, Series> {

    override fun map(input: SeriesEntity): Series =
        Series(
            input.id,
            input.rating,
            input.title,
            input.imageUrl,
        )

}