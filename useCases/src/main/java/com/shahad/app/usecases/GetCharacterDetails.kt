package com.shahad.app.usecases

import com.shahad.app.core.DetailsScreenState
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Series
import com.shahad.app.core.models.Story
import com.shahad.app.repositories.repositories.CharactersRepository
import com.shahad.app.repositories.repositories.StoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharacterDetails @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val storiesRepository: StoriesRepository,
) {
    fun getCharacter(characterId: Long): Flow<DetailsScreenState<Character?>> {
        return charactersRepository.getCharacterDetails(characterId).map { it?.firstOrNull() }.mapToDetailsState()
    }

    fun getCharacterSeries(characterId: Long): Flow<DetailsScreenState<List<Story>?>> {
        return storiesRepository.getCharacterStories(characterId).mapToDetailsState()
    }
}