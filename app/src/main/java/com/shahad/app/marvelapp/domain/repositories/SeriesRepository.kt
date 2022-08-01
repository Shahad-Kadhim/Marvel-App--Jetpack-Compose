package com.shahad.app.marvelapp.domain.repositories

import com.shahad.app.marvelapp.domain.models.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    suspend fun getSeries(numberOfSeries: Int = 10): Flow<List<Series>>
    suspend fun refreshSeries(numberOfSeries: Int)
}