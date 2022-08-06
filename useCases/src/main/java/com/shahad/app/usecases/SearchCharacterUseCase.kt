package com.shahad.app.usecases

import com.shahad.app.repositories.repositories.CharactersRepository
import javax.inject.Inject

class SearchCharacterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(keyWord: String) = charactersRepository.searchCharacter(keyWord)
}