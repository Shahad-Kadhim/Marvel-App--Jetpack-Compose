package com.shahad.app.repositories.repositories

import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Series
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(numberOfCharacter: Int = 10): Flow<List<Character>>
    suspend fun refreshCharacters(numberOfCharacter: Int)
    fun searchCharacter(keyWord: String): Flow<List<Character>?>
    fun getCharacterDetails(characterId: Long): Flow<List<Character>?>
}