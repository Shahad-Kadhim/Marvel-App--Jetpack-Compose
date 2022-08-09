package com.shahad.app.repositories.repositories

import androidx.paging.*
import com.shahad.app.data.local.MarvelDao
import com.shahad.app.data.mappers.LocalMappers
import com.shahad.app.data.remote.MarvelService
import com.shahad.app.repositories.mappers.DomainMapper
import com.shahad.app.core.models.Character
import com.shahad.app.data.CharacterRemoteMediator
import com.shahad.app.data.local.MarvelDatabase
import com.shahad.app.data.toImageUrl
import com.shahad.app.repositories.CharacterSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersRepositoryImp @Inject constructor(
    private val api: MarvelService,
    private val dao: MarvelDao,
    private val database: MarvelDatabase,
    private val domainMappers: DomainMapper,
    private val localMappers: LocalMappers
): CharactersRepository, BaseRepository {

    @OptIn(ExperimentalPagingApi::class)
    override  fun getCharacter(searchKeyword: String?): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = CharacterRemoteMediator(
                searchKeyword = searchKeyword,
                api = api,
                database =database,
                dao = dao,
                localMappers =localMappers,
            ),
            pagingSourceFactory = { dao.getCharacterPage(searchKeyword?.replace(' ','%') ?: "%")}
        ).flow.map {
            it.map(domainMappers.characterMapper::map)
        }
    }



    override fun searchCharacterWithName(keyWord: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterSource(api, keyWord )}
        ).flow
    }

    override fun getCharacterDetails(characterId: Long): Flow<List<Character>?> {
        return wrapWithFlow(
            request = { api.getCharacterById(characterId)},
            mapper = { Character(it.id, it.name, it.thumbnail.toImageUrl(),it.description ?: "No Description" )}
        )
    }

}