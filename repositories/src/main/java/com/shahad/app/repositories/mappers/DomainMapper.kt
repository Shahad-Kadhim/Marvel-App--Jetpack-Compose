package com.shahad.app.repositories.mappers

import javax.inject.Inject

class DomainMapper @Inject constructor(
    val characterMapper: CharacterMapper,
    val seriesMapper: SeriesMapper,
    val creatorMapper: CreatorMapper,
)
