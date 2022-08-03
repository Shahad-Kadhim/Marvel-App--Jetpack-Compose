package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.data.repositories.SeriesRepository
import com.shahad.app.marvelapp.util.wrapWithHomeStates
import javax.inject.Inject

class GetSeriesUseCase  @Inject constructor(
    private val seriesRepository: SeriesRepository
){
    suspend operator fun invoke(numberOfSeries: Int) = seriesRepository.getSeries(numberOfSeries).wrapWithHomeStates()
}