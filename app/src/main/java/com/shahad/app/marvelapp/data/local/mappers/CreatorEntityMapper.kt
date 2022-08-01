package com.shahad.app.marvelapp.data.local.mappers

import com.shahad.app.marvelapp.data.local.entities.CreatorEntity
import com.shahad.app.marvelapp.data.remote.response.CreatorDto
import com.shahad.app.marvelapp.domain.mappers.Mapper
import com.shahad.app.marvelapp.util.toImageUrl
import javax.inject.Inject

class CreatorEntityMapper @Inject constructor(): Mapper<CreatorDto, CreatorEntity> {

    override fun map(input: CreatorDto): CreatorEntity =
        CreatorEntity(
            id = input.id,
            name =input.name,
            imageUrl = input.thumbnail.toImageUrl(),
            lastModify = input.modified
        )

}