package com.shahad.app.repositories.mappers

import com.shahad.app.data.Mapper
import com.shahad.app.data.local.entities.CreatorEntity
import com.shahad.app.core.models.Creator
import javax.inject.Inject

class CreatorMapper @Inject constructor(): Mapper<CreatorEntity, Creator> {

    override fun map(input: CreatorEntity): Creator =
        Creator(
            id = input.id,
            name = input.name,
            imageUrl = input.imageUrl
        )
}