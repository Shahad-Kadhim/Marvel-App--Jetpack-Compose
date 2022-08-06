package com.shahad.app.data.mappers

import javax.inject.Inject

class LocalMappers @Inject constructor(
    val characterEntityMapper: CharacterEntityMapper,
    val seriesEntityMapper: SeriesEntityMapper,
    val creatorEntityMapper: CreatorEntityMapper,
)