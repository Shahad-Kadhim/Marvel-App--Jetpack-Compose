package com.shahad.app.marvelapp.data.repositories

import com.shahad.app.marvelapp.data.SearchScreenState
import com.shahad.app.marvelapp.data.local.MarvelDao
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.remote.MarvelService
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.models.Character
import com.shahad.app.marvelapp.util.convertTo
import com.shahad.app.marvelapp.util.toImageUrl
import com.shahad.app.marvelapp.util.toModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepositoryImp @Inject constructor(
    private val api: MarvelService,
    private val dao: MarvelDao,
    private val domainMappers: DomainMapper,
    private val localMappers: LocalMappers
): CharactersRepository, BaseRepository {

    override suspend fun getCharacters(numberOfCharacter: Int): Flow<List<Character>> {
        refreshCharacters(numberOfCharacter)
        return dao.getCharacters()
            .convertTo(domainMappers.characterMapper::map)
    }


    override suspend fun refreshCharacters(numberOfCharacter: Int) {
        refreshDataBase(
            api::getCharacters ,
            dao::insertCharacters,
            numberOfCharacter,
        ){ body ->
            body?.data?.results?.map { characterDto ->
                localMappers.characterEntityMapper.map(characterDto)
            }
        }
    }

    override fun searchCharacter(keyWord: String): Flow<SearchScreenState<List<Character>?>?> {
        return wrapWithFlowOfSearchState { api.getCharacters(searchKeyWord = keyWord) }.toModel {
            Character(it.id,it.name,it.thumbnail.toImageUrl(),it.description ?: "No Description")
        }
    }

}