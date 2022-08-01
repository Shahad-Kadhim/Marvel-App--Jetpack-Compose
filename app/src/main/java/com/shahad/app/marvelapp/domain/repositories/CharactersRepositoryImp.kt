package com.shahad.app.marvelapp.domain.repositories

import com.shahad.app.marvelapp.data.local.MarvelDao
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.remote.MarvelService
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.models.Character
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
        return wrapper(
            dao.getCharacters() ,
            domainMappers.characterMapper::map,
        )
    }

    override suspend fun refreshCharacters(numberOfCharacter: Int) {
        refreshWrapper(
            api::getCharacters ,
            dao::insertCharacters,
            numberOfCharacter,
        ){ body ->
            body?.data?.results?.map { characterDto ->
                localMappers.characterEntityMapper.map(characterDto)
            }
        }
    }


}