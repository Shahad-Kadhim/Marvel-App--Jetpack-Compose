package com.shahad.app.usecases

import com.shahad.app.core.wrapWithHomeStates
import com.shahad.app.repositories.repositories.SeriesRepository
import javax.inject.Inject

class GetSeriesUseCase  @Inject constructor(
    private val seriesRepository: SeriesRepository
){
    suspend operator fun invoke(numberOfSeries: Int) =
        seriesRepository.getSeries(numberOfSeries).wrapWithHomeStates()
}