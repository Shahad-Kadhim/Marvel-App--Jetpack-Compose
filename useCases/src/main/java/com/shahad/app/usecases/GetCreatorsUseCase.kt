package com.shahad.app.usecases

import com.shahad.app.core.wrapWithHomeStates
import com.shahad.app.repositories.repositories.CreatorsRepository
import javax.inject.Inject

class GetCreatorsUseCase  @Inject constructor(
    private val creatorsRepository: CreatorsRepository
){
    suspend operator fun invoke(numberOfCreators: Int) =
        creatorsRepository.getCreators(numberOfCreators).wrapWithHomeStates()
}