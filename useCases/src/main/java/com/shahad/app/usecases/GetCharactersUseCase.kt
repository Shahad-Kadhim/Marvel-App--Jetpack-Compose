package com.shahad.app.usecases

import com.shahad.app.core.HomeScreenState
import com.shahad.app.core.wrapWithHomeStates
import com.shahad.app.core.models.Character
import com.shahad.app.repositories.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(numberOfCharacters: Int): Flow<HomeScreenState<List<Character>>> =
        charactersRepository.getCharacters(numberOfCharacters).wrapWithHomeStates()

}