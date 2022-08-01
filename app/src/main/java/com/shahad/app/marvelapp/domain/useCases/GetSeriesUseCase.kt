package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.domain.repositories.SeriesRepository
import javax.inject.Inject

class GetSeriesUseCase  @Inject constructor(
    private val seriesRepository: SeriesRepository
){
    suspend operator fun invoke(numberOfSeries: Int) = seriesRepository.getSeries(numberOfSeries)
}