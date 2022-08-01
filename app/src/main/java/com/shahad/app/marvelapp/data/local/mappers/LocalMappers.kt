package com.shahad.app.marvelapp.data.local.mappers

import javax.inject.Inject

class LocalMappers @Inject constructor(
    val characterEntityMapper: CharacterEntityMapper,
    val seriesEntityMapper: SeriesEntityMapper,
    val creatorEntityMapper: CreatorEntityMapper,
)