package com.shahad.app.usecases

import com.shahad.app.repositories.repositories.CreatorsRepository
import javax.inject.Inject

class SearchCreatorUseCase @Inject constructor(
    private val creatorsRepository: CreatorsRepository
) {
    operator fun invoke(keyWord: String) = creatorsRepository.searchCreator(keyWord)
}