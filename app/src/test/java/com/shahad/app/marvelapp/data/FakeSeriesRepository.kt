package com.shahad.app.marvelapp.data

import com.shahad.app.marvelapp.data.local.entities.FavoriteEntity
import com.shahad.app.marvelapp.data.local.entities.SeriesEntity
import com.shahad.app.marvelapp.data.local.mappers.CharacterEntityMapper
import com.shahad.app.marvelapp.data.local.mappers.CreatorEntityMapper
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.local.mappers.SeriesEntityMapper
import com.shahad.app.marvelapp.data.remote.response.SeriesDto
import com.shahad.app.marvelapp.data.repositories.SeriesRepository
import com.shahad.app.marvelapp.domain.mappers.*
import com.shahad.app.marvelapp.domain.models.Series
import com.shahad.app.marvelapp.util.toImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSeriesRepository: SeriesRepository{


    var domainMapper: DomainMapper = DomainMapper(
        CharacterMapper(), SeriesMapper(),
        CreatorMapper(), FavoriteSeriesMapper()
    )

    var localMapper: LocalMappers = LocalMappers(
        CharacterEntityMapper(), SeriesEntityMapper(),
        CreatorEntityMapper()
    )

    private var shouldReturnError = false

    private val remoteSeries = mutableMapOf(
        Pair(1,SeriesDto(id = 1, title = "s1", description = "d1", modified = "2020/2/1")),
        Pair(2,SeriesDto(id = 2, title = "s2", description = "d2", modified = "2020/2/1")),
    )

    private  val favoriteSeries =  mutableMapOf<Int,FavoriteEntity>()

    private val localSeries = mutableMapOf<Int,SeriesEntity>()


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

    override fun searchSeries(keyWord: String): Flow<SearchScreenState<List<Series>?>?> {
        return flow {
            emit(SearchScreenState.Loading)
            val series = remoteSeries.values.filter { it.title.contains(keyWord) }.map {
                Series(
                    id = it.id,
                    title = it.title,
                    imageUrl = it.thumbnail.toImageUrl(),
                    rating = "5"
                )
            }
            if (shouldReturnError) {
                emit(SearchScreenState.Error("fake error"))
            } else {
                takeIf { series.isNotEmpty() }?.let {
                    emit(SearchScreenState.Success(series))
                } ?: run {
                    emit(SearchScreenState.Empty)
                }
            }
        }
    }

    override fun getFavoriteSeries(): Flow<FavouriteScreenState<List<Series>>> {
        return flow {
            emit(FavouriteScreenState.Loading)
            favoriteSeries.takeIf { it.isNotEmpty() }?.let {
                emit(FavouriteScreenState.Success(it.values.map(domainMapper.favoriteSeriesMapper::map)))
            } ?: run{
                emit(FavouriteScreenState.Empty)
            }
        }
    }

    override suspend fun addFavouriteSeries(series: Series) {
        favoriteSeries[series.id] = domainMapper.favoriteSeriesMapper.inverseMap(series)
    }

    override suspend fun deleteFavouriteSeries(seriesId: Int) {
        favoriteSeries.remove(seriesId)
    }

    override suspend fun clearFavoriteSeries() {
        favoriteSeries.clear()
    }
}