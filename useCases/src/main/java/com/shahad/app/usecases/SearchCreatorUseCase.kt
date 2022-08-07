package com.shahad.app.usecases

import com.shahad.app.core.SearchScreenState
import com.shahad.app.core.models.Creator
import com.shahad.app.repositories.repositories.CreatorsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCreatorUseCase @Inject constructor(
    private val creatorsRepository: CreatorsRepository
) {
    operator fun invoke(keyWord: String): Flow<SearchScreenState<List<Creator>?>> =
        creatorsRepository.searchCreator(keyWord).mapToSearchState()
}