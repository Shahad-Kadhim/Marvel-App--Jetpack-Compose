package com.shahad.app.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.shahad.app.core.models.Character
import com.shahad.app.repositories.CharacterSource
import com.shahad.app.repositories.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
) {
     operator fun invoke(searchKeyword: String?  = null): Flow<PagingData<Character>> {
         return charactersRepository.getCharacter(searchKeyword)
    }
}