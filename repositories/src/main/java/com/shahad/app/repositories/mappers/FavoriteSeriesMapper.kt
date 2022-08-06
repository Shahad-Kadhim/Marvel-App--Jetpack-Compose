package com.shahad.app.repositories.mappers

import com.shahad.app.data.Mapper
import com.shahad.app.data.local.entities.FavoriteEntity
import com.shahad.app.core.models.Series
import javax.inject.Inject

class FavoriteSeriesMapper @Inject constructor(): Mapper<FavoriteEntity, Series> {

    override fun map(input: FavoriteEntity): Series =
        Series(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
            isFavourite = true
        )

    fun inverseMap(input: Series): FavoriteEntity =
        FavoriteEntity(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
        )
}