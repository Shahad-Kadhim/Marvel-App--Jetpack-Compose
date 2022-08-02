package com.shahad.app.marvelapp.domain.mappers

import com.shahad.app.marvelapp.data.local.entities.CharacterEntity
import com.shahad.app.marvelapp.domain.models.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor(): Mapper<CharacterEntity,Character> {

    override fun map(input: CharacterEntity): Character =
        Character(
            id = input.id,
            name = input.name,
            image =input.imageUrl,
            description = input.description ?: "No Description Available"
        )

}