package com.shahad.app.repositories.repositories

import com.shahad.app.core.models.Story
import com.shahad.app.data.local.MarvelDao
import com.shahad.app.data.mappers.LocalMappers
import com.shahad.app.data.remote.MarvelService
import com.shahad.app.repositories.mappers.DomainMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoriesRepositoryImp @Inject constructor(
    private val api: MarvelService,
    private val dao: MarvelDao,
    private val domainMappers: DomainMapper,
    private val localMappers: LocalMappers
): StoriesRepository , BaseRepository {

    override fun getCharacterStories(characterId: Long): Flow<List<Story>?> {
        return wrapWithFlow(
            request = { api.getCharacterStoriesById(characterId)},
            mapper = { Story(it.title ?: "",it.description ?: "",it.id)}
        )
    }

}