package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.data.HomeScreenState
import com.shahad.app.marvelapp.domain.models.Character
import com.shahad.app.marvelapp.data.repositories.CharactersRepository
import com.shahad.app.marvelapp.util.wrapWithHomeStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(numberOfCharacters: Int): Flow<HomeScreenState<List<Character>>> =
        charactersRepository.getCharacters(numberOfCharacters).wrapWithHomeStates()
}