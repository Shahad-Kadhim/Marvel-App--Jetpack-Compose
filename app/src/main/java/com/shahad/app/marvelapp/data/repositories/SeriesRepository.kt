package com.shahad.app.marvelapp.data.repositories

import com.shahad.app.marvelapp.data.SearchScreenState
import com.shahad.app.marvelapp.domain.models.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    suspend fun getSeries(numberOfSeries: Int = 10): Flow<List<Series>>
    suspend fun refreshSeries(numberOfSeries: Int)
     fun searchSeries(keyWord: String): Flow<SearchScreenState<List<Series>?>?>
}