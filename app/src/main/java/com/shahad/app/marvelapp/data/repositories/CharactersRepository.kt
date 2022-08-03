package com.shahad.app.marvelapp.data.repositories

import com.shahad.app.marvelapp.data.SearchScreenState
import com.shahad.app.marvelapp.domain.models.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(numberOfCharacter: Int = 10): Flow<List<Character>>
    suspend fun refreshCharacters(numberOfCharacter: Int)
    fun searchCharacter(keyWord: String): Flow<SearchScreenState<List<Character>?>?>
}