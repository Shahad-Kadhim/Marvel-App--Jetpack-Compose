package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.data.repositories.SeriesRepository
import javax.inject.Inject

class SearchSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(keyWord: String) = seriesRepository.searchSeries(keyWord)
}