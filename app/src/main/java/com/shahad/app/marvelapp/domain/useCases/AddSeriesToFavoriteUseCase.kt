package com.shahad.app.marvelapp.domain.useCases

import com.shahad.app.marvelapp.data.repositories.SeriesRepository
import com.shahad.app.marvelapp.domain.models.Series
import javax.inject.Inject

class AddSeriesToFavoriteUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(series: Series)=
        seriesRepository.addFavouriteSeries(series)

}