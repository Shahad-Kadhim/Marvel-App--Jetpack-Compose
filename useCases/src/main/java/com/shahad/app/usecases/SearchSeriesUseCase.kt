package com.shahad.app.usecases

import com.shahad.app.repositories.repositories.SeriesRepository
import javax.inject.Inject

class SearchSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(keyWord: String) = seriesRepository.searchSeries(keyWord)
}