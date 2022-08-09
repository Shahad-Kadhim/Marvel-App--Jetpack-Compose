package com.shahad.app.usecases.fakeRepositories

import androidx.paging.PagingData
import com.shahad.app.core.models.Series
import com.shahad.app.data.local.entities.SeriesEntity
import com.shahad.app.data.mappers.*
import com.shahad.app.data.remote.response.SeriesDto
import com.shahad.app.repositories.mappers.*
import com.shahad.app.repositories.repositories.SeriesRepository
import com.shahad.app.repositories.toSeries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSeriesRepository: SeriesRepository {


    var domainMapper: DomainMapper = DomainMapper(
        CharacterMapper(), SeriesMapper(),
        CreatorMapper()
    )

    var localMapper: LocalMappers = LocalMappers(
        CharacterEntityMapper(), SeriesEntityMapper(),
        CreatorEntityMapper()
    )

    private var shouldReturnError = false

    private val remoteSeries = mutableMapOf<Long,SeriesDto>(
        Pair(1, SeriesDto(id = 1, title = "s1", description = "d1", modified = "2020/2/1")),
        Pair(2, SeriesDto(id = 2, title = "s2", description = "d2", modified = "2020/2/1")),
    )


    private val localSeries = mutableMapOf<Long, SeriesEntity>()

    private fun getFavouriteSeries() = localSeries.filter { it.value.isFavorite }.toMutableMap()

    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }


    fun updateRemoteSeries(){
        remoteSeries[3] = SeriesDto(id = 3, title =  "c3", modified = "2020/3/3")
    }

    fun getSizeOfRemoteSeries() = remoteSeries.size


    override suspend fun getSeries(numberOfSeries: Int): Flow<List<Series>> {
        refreshSeries(numberOfSeries)
        return flow {
            emit(localSeries.map { domainMapper.seriesMapper.map(it.value) })
        }
    }

    override suspend fun refreshSeries(numberOfSeries: Int) {
        takeUnless { shouldReturnError }?.let {
            localSeries.putAll(remoteSeries.map { Pair(it.key,localMapper.seriesEntityMapper.map(it.value)) })
        }
    }


    override fun getFavoriteSeries(): Flow<List<Series>> {
        return flow {
            emit(getFavouriteSeries().values.map(domainMapper.seriesMapper::map))
        }
    }

    override suspend fun addFavouriteSeries(series: Series) {
        localSeries.get(series.id)?.let {
            it.isFavorite = true
        } ?: run {
            localSeries[series.id] =
                domainMapper.seriesMapper.inverseMap(series.apply { isFavourite = true })
        }

    }

    override suspend fun deleteFavouriteSeries(seriesId: Long) {
        localSeries[seriesId]?.apply {
            isFavorite = false
        }
    }

    override suspend fun clearFavoriteSeries() {
        getFavouriteSeries().forEach{
            localSeries[it.key]?.isFavorite =false
        }
    }

    override fun searchSeriesWithName(keyWord: String): Flow<PagingData<Series>> {
        return flow {
            emit(PagingData.from(remoteSeries.values.filter { it.title?.contains(keyWord) == true }.map { it.toSeries() }))
        }
    }

}