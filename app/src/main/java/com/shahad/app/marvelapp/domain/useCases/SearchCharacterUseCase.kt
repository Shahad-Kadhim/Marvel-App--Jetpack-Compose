package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.data.repositories.CharactersRepository
import javax.inject.Inject

class SearchCharacterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(keyWord: String) = charactersRepository.searchCharacter(keyWord)
}