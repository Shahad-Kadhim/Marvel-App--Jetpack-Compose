package com.shahad.app.usecases

import androidx.paging.PagingData
import com.shahad.app.core.models.Character
import com.shahad.app.repositories.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCharacterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(keyWord: String): Flow<PagingData<Character>> =
        charactersRepository.searchCharacterWithName(keyWord)
}