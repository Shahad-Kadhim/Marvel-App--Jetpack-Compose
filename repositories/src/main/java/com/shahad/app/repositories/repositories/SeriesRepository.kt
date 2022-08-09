package com.shahad.app.repositories.repositories

import androidx.paging.PagingData
import com.shahad.app.core.models.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    suspend fun getSeries(numberOfSeries: Int = 10): Flow<List<Series>>
    suspend fun refreshSeries(numberOfSeries: Int)
    fun getFavoriteSeries(): Flow<List<Series>>
    suspend fun addFavouriteSeries(series: Series)
    suspend fun deleteFavouriteSeries(seriesId: Int)
    suspend fun clearFavoriteSeries()
    fun searchSeriesWithName(keyWord: String): Flow<PagingData<Series>>

}