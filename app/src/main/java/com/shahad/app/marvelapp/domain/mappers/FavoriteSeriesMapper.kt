package com.shahad.app.marvelapp.domain.mappers

import com.shahad.app.marvelapp.data.local.entities.FavoriteEntity
import com.shahad.app.marvelapp.data.local.entities.SeriesEntity
import com.shahad.app.marvelapp.domain.models.Series
import javax.inject.Inject

class FavoriteSeriesMapper @Inject constructor(): Mapper<FavoriteEntity, Series> {

    override fun map(input: FavoriteEntity): Series =
        Series(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl,
        )

    fun inverseMap(input: Series): FavoriteEntity =
        FavoriteEntity(
            id = input.id,
            title = input.title,
            imageUrl = input.imageUrl
        )
}