package com.shahad.app.repositories

import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Creator
import com.shahad.app.core.models.Series
import com.shahad.app.data.remote.response.CharacterDto
import com.shahad.app.data.remote.response.CreatorDto
import com.shahad.app.data.remote.response.SeriesDto
import com.shahad.app.data.toImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun Series.setIsFavourite(value: Boolean) =
    this.apply {
        isFavourite = value
    }

fun <T, U> Flow<List<T>>.convertTo(
    mapper: (T) -> U
): Flow<List<U>> =
    this.map { list ->
        list.map { entity ->
            mapper(entity)
        }
    }

fun CharacterDto.toCharacter() =
    Character(
        id = this.id,
        name = this.name,
        description = this.description ?: "No Description",
        image = this.thumbnail.toImageUrl()
    )


fun CreatorDto.toCreator() =
    Creator(
        id = this.id,
        name = this.name,
        imageUrl = this.thumbnail.toImageUrl()
    )



fun SeriesDto.toSeries() =
    Series(
        id = this.id,
        title = this.title ?: "UNKNOWN TITLE",
        imageUrl = this.thumbnail.toImageUrl()
    )

