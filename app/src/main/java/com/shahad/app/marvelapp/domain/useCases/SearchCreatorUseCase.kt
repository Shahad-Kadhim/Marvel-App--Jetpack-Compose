package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.data.repositories.CreatorsRepository
import javax.inject.Inject

class SearchCreatorUseCase @Inject constructor(
    private val creatorsRepository: CreatorsRepository
) {
    operator fun invoke(keyWord: String) = creatorsRepository.searchCreator(keyWord)
}