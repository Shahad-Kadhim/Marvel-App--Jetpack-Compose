package com.shahad.app.data.mappers

import com.shahad.app.data.local.entities.CharacterEntity
import com.shahad.app.data.toImageUrl
import com.shahad.app.data.Mapper
import com.shahad.app.data.remote.response.CharacterDto
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

