package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.domain.models.Character
import com.shahad.app.marvelapp.data.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(numberOfCharacters: Int): Flow<List<Character>> = charactersRepository.getCharacters(numberOfCharacters)
}