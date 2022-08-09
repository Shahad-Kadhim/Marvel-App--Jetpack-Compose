package com.shahad.app.usecases

import androidx.paging.PagingData
import com.shahad.app.core.models.Series
import com.shahad.app.repositories.repositories.SeriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(keyWord: String): Flow<PagingData<Series>> =
        seriesRepository.searchSeriesWithName(keyWord)
}