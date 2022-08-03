package com.shahad.app.marvelapp.data

import com.shahad.app.marvelapp.data.local.entities.CharacterEntity
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.remote.response.CharacterDto
import com.shahad.app.marvelapp.data.repositories.CharactersRepository
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.models.Character
import com.shahad.app.marvelapp.util.toImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeCharacterRepository: CharactersRepository{

    @Inject
    lateinit var domainMapper: DomainMapper

    @Inject
    lateinit var localMapper: LocalMappers

    private var shouldReturnError = false

    private val remoteCharacters = mutableMapOf(
        Pair(1,CharacterDto(id = 1, name = "c1", description = "d1", modified = "2020/2/1")),
        Pair(2,CharacterDto(id = 2, name = "c2", description = "d2", modified = "2020/2/1")),
    )

    private val localCharacters = mutableMapOf<Int,CharacterEntity>()


    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }

    override suspend fun getCharacters(numberOfCharacter: Int): Flow<HomeScreenState<List<Character>>> {
        refreshCharacters(numberOfCharacter)
        return flow {
            emit(HomeScreenState.Success(localCharacters.map { domainMapper.characterMapper.map(it.value) }))
        }
    }

    override suspend fun refreshCharacters(numberOfCharacter: Int) {
        localCharacters.putAll(remoteCharacters.map { Pair(it.key,localMapper.characterEntityMapper.map(it.value)) })
    }

    override fun searchCharacter(keyWord: String): Flow<SearchScreenState<List<Character>?>?> {
        val characters = remoteCharacters.values.filter { it.name.matches(keyWord.toRegex()) }.map{
            Character(it.id,it.name,it.thumbnail.toImageUrl(),it.description ?: "No Description")
        }
        return if(shouldReturnError){
            flow { SearchScreenState.Error("fake error") }
        }else {
            flow {
                emit(SearchScreenState.Success(characters))
            }
        }
    }

}