package com.shahad.app.usecases

import com.shahad.app.core.SearchScreenState
import com.shahad.app.core.models.Character
import com.shahad.app.repositories.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCharacterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(keyWord: String): Flow<SearchScreenState<List<Character>?>> =
        charactersRepository.searchCharacter(keyWord).mapToSearchState()
}