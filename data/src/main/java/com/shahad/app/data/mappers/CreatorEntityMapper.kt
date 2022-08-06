package com.shahad.app.data.mappers

import com.shahad.app.data.local.entities.CreatorEntity
import com.shahad.app.data.Mapper
import com.shahad.app.data.remote.response.CreatorDto
import com.shahad.app.data.toImageUrl
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