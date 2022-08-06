package com.shahad.app.usecases

import com.shahad.app.core.models.Series
import com.shahad.app.repositories.repositories.SeriesRepository
import javax.inject.Inject

class AddSeriesToFavoriteUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(series: Series)=
        seriesRepository.addFavouriteSeries(series)

}