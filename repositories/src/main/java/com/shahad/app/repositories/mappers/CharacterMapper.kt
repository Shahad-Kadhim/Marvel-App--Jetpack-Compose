package com.shahad.app.repositories.mappers

import com.shahad.app.data.Mapper
import com.shahad.app.data.local.entities.CharacterEntity
import com.shahad.app.core.models.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor():
    Mapper<CharacterEntity, Character> {

    override fun map(input: CharacterEntity): Character =
        Character(
            id = input.id,
            name = input.name,
            image =input.imageUrl,
            description = input.description ?: "No Description Available"
        )

}