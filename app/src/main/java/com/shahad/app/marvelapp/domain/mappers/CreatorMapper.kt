package com.shahad.app.marvelapp.domain.mappers

import com.shahad.app.marvelapp.domain.models.Creator
import com.shahad.app.marvelapp.data.local.entities.CreatorEntity
import javax.inject.Inject

class CreatorMapper @Inject constructor(): Mapper<CreatorEntity, Creator> {

    override fun map(input: CreatorEntity): Creator =
        Creator(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl
        )
}