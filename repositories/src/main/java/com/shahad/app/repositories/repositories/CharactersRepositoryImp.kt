package com.shahad.app.repositories.repositories

import com.shahad.app.data.local.MarvelDao
import com.shahad.app.data.mappers.LocalMappers
import com.shahad.app.data.remote.MarvelService
import com.shahad.app.repositories.mappers.DomainMapper
import com.shahad.app.core.models.Character
import com.shahad.app.data.toImageUrl
import com.shahad.app.repositories.convertTo
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

    override fun searchCharacter(keyWord: String): Flow<List<Character>?> {
        return wrapWithFlow(
            request = { api.getCharacters(searchKeyWord = keyWord)},
            mapper = {
                Character(it.id,it.name,it.thumbnail.toImageUrl(),it.description ?: "No Description")
            }
        )
    }


    override fun getCharacterDetails(characterId: Long): Flow<List<Character>?> {
        return wrapWithFlow(
            request = { api.getCharacterById(characterId)},
            mapper = { Character(it.id, it.name, it.thumbnail.toImageUrl(),it.description ?: "No Description" )}
        )
    }

}