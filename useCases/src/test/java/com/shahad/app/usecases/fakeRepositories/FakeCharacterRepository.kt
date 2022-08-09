package com.shahad.app.usecases.fakeRepositories

import androidx.paging.PagingData
import com.shahad.app.data.local.entities.CharacterEntity
import com.shahad.app.data.mappers.*
import com.shahad.app.data.remote.response.CharacterDto
import com.shahad.app.repositories.mappers.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.shahad.app.core.models.Character
import com.shahad.app.repositories.repositories.CharactersRepository
import com.shahad.app.repositories.toCharacter

class FakeCharacterRepository: CharactersRepository {

    private var shouldReturnError = false

    val characterEntityMappers = CharacterEntityMapper()
    val characterMappers = CharacterMapper()
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

    override fun getCharacterDetails(characterId: Long): Flow<List<Character>?> {
        return flow {
            emit(remoteCharacters.values.filter { it.id == characterId }.map { it.toCharacter() })
        }
    }

    override fun getCharacter(searchKeyword: String?): Flow<PagingData<Character>> {
        return flow{
            try {
                var entry = 0
                while (entry < localCharacters.size){
                    emit(PagingData.from(localCharacters.values.map(characterMappers::map).subList(entry,entry+10)))
                    entry =+ 10
                }
            }catch (e:Exception){

            }finally {
                localCharacters.putAll(remoteCharacters.map { Pair(it.key,characterEntityMappers.map(it.value)) })
                emit(PagingData.from(localCharacters.values.map(characterMappers::map)))
            }
        }
    }

    override fun searchCharacterWithName(keyWord: String): Flow<PagingData<Character>> {
        return flow{
            emit(PagingData.from(remoteCharacters.values.filter { it.name.contains(keyWord) }.map { it.toCharacter() }))
        }
    }

    fun getLocalCharacter() = localCharacters.values.map(characterMappers::map)

}