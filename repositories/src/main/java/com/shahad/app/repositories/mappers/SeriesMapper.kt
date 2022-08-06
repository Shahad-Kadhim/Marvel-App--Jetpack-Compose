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
            isFavourite = false
        )

}