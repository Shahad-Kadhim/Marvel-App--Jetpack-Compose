package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.data.repositories.SeriesRepository
import javax.inject.Inject

class DeleteSeriesFromFavoriteUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(seriesId: Int)=
        seriesRepository.deleteFavouriteSeries(seriesId)

}