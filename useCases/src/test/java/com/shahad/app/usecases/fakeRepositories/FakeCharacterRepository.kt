package com.shahad.app.usecases.fakeRepositories

import com.shahad.app.data.local.entities.CharacterEntity
import com.shahad.app.data.mappers.*
import com.shahad.app.data.remote.response.CharacterDto
import com.shahad.app.repositories.mappers.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.shahad.app.data.toImageUrl
import com.shahad.app.core.models.Character
import com.shahad.app.repositories.repositories.CharactersRepository

class FakeCharacterRepository: CharactersRepository {

    var domainMapper: DomainMapper = DomainMapper(
        CharacterMapper(), SeriesMapper(),
        CreatorMapper(), FavoriteSeriesMapper()
    )

    var localMapper: LocalMappers = LocalMappers(CharacterEntityMapper(), SeriesEntityMapper(),
        CreatorEntityMapper()
    )

    private var shouldReturnError = false

    private val remoteCharacters = mutableMapOf(
        Pair(1, CharacterDto(id = 1, name = "c1", description = "d1", modified = "2020/2/1")),
        Pair(2,CharacterDto(id = 2, name = "c2", description = "d2", modified = "2020/2/1")),
    )

    private val localCharacters = mutableMapOf<Int, CharacterEntity>()


    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }

    fun updateRemoteCharacter(){
        remoteCharacters[3] = CharacterDto(id = 3, name = "c3", description = "d3", modified = "2020/3/3")
    }

    fun getSizeOfRemoteCharacters() = remoteCharacters.size

    override suspend fun getCharacters(numberOfCharacter: Int): Flow<List<Character>> {
        refreshCharacters(numberOfCharacter)
        return flow {
            emit(localCharacters.map { domainMapper.characterMapper.map(it.value) })
        }
    }

    override suspend fun refreshCharacters(numberOfCharacter: Int) {
        takeUnless { shouldReturnError }?.let {
            localCharacters.putAll(remoteCharacters.map { Pair(it.key,localMapper.characterEntityMapper.map(it.value)) })
        }
    }

    override fun searchCharacter(keyWord: String): Flow<List<Character>?> {

        return  flow {
            if(shouldReturnError){
                emit(null)
            }else {
                val characters = remoteCharacters.values.filter { it.name.contains(keyWord)}.map{
                    Character(it.id,it.name,it.thumbnail.toImageUrl(),it.description ?: "No Description")
                }
                emit(characters)
            }
        }
    }

}