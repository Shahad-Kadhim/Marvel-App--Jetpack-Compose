package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.data.repositories.CreatorsRepository
import com.shahad.app.marvelapp.util.wrapWithHomeStates
import javax.inject.Inject

class GetCreatorsUseCase  @Inject constructor(
    private val creatorsRepository: CreatorsRepository
){
    suspend operator fun invoke(numberOfCreators: Int) =
        creatorsRepository.getCreators(numberOfCreators).wrapWithHomeStates()
}