package com.shahad.app.repositories.repositories

import com.shahad.app.core.models.Story
import kotlinx.coroutines.flow.Flow

interface StoriesRepository {
    fun getCharacterStories(characterId: Long): Flow<List<Story>?>
}