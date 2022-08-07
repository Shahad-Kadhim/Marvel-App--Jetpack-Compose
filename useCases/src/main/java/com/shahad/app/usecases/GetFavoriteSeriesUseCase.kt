package com.shahad.app.usecases

import com.shahad.app.core.FavouriteScreenState
import com.shahad.app.core.models.Series
import com.shahad.app.repositories.repositories.SeriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(): Flow<FavouriteScreenState<List<Series>>> =
        seriesRepository.getFavoriteSeries().mapToFavouriteState()
}