package com.shahad.app.repositories.repositories

import androidx.paging.PagingData
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Series
import com.shahad.app.data.local.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacterDetails(characterId: Long): Flow<List<Character>?>
    fun getCharacter(searchKeyword: String?): Flow<PagingData<Character>>
    fun searchCharacterWithName(keyWord: String): Flow<PagingData<Character>>
}