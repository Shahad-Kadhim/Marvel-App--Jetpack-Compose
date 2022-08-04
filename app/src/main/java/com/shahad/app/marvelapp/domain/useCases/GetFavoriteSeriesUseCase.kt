package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.data.FavouriteScreenState
import com.shahad.app.marvelapp.data.repositories.SeriesRepository
import com.shahad.app.marvelapp.domain.models.Series
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(): Flow<FavouriteScreenState<List<Series>>> =
        seriesRepository.getFavoriteSeries()

}