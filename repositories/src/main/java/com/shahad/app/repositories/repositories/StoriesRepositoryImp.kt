package com.shahad.app.repositories.repositories

import com.shahad.app.core.models.Story
import com.shahad.app.data.remote.MarvelService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoriesRepositoryImp @Inject constructor(
    private val api: MarvelService,
): StoriesRepository , BaseRepository {

    override fun getCharacterStories(characterId: Long): Flow<List<Story>?> {
        return wrapWithFlow(
            request = { api.getCharacterStoriesById(characterId)},
            mapper = { Story(it.title ?: "",it.description ?: "",it.id)}
        )
    }

}