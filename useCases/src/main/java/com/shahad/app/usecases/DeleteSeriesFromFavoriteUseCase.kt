package com.shahad.app.usecases

import com.shahad.app.repositories.repositories.SeriesRepository
import javax.inject.Inject

class DeleteSeriesFromFavoriteUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int)=
        seriesRepository.deleteFavouriteSeries(seriesId)

    suspend fun clearFavouriteSeries() = seriesRepository.clearFavoriteSeries()
}