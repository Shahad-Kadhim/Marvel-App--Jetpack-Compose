package com.shahad.app.marvelapp.data.local.mappers

import com.shahad.app.marvelapp.data.local.entities.CharacterEntity
import com.shahad.app.marvelapp.data.remote.response.CharacterDto
import com.shahad.app.marvelapp.domain.mappers.Mapper
import com.shahad.app.marvelapp.util.toImageUrl
import javax.inject.Inject

class CharacterEntityMapper @Inject constructor(): Mapper<CharacterDto, CharacterEntity> {

    override fun map(input: CharacterDto): CharacterEntity =
        CharacterEntity(
            id = input.id,
            name = input.name,
            imageUrl = input.thumbnail.toImageUrl(),
            modified = input.modified,
            description = input.description
        )
}

