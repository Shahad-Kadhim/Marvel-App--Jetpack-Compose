package com.shahad.app.repositories.repositories

import com.shahad.app.core.FavouriteScreenState
import com.shahad.app.core.SearchScreenState
import com.shahad.app.data.local.MarvelDao
import com.shahad.app.data.local.entities.FavoriteEntity
import com.shahad.app.data.mappers.LocalMappers
import com.shahad.app.data.remote.MarvelService
import com.shahad.app.data.toImageUrl
import com.shahad.app.repositories.mappers.DomainMapper
import com.shahad.app.core.models.Series
import com.shahad.app.repositories.convertTo
import com.shahad.app.repositories.toModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SeriesRepositoryImp @Inject constructor(
    private val api: MarvelService,
    private val dao: MarvelDao,
    private val domainMappers: DomainMapper,
    private val localMappers: LocalMappers
): SeriesRepository, BaseRepository {
    override suspend fun getSeries(numberOfSeries: Int): Flow<List<Series>> {
        refreshSeries(numberOfSeries)
        return dao.getSeries()
            .convertTo(domainMappers.seriesMapper::map)
    }

    override suspend fun refreshSeries(numberOfSeries: Int) {
        refreshDataBase(
            api::getSeries,
            dao::insertSeries,
            numberOfSeries,
        ){ body ->
            body?.data?.results?.map { seriesDto ->
                localMappers.seriesEntityMapper.map(seriesDto)
            }
        }
    }

    override fun searchSeries(keyWord: String): Flow<SearchScreenState<List<Series>?>?> {
        return wrapWithFlowOfSearchState { api.getSeries(searchKeyWord = keyWord) }.toModel {
            Series(it.id,it.rating,it.title,it.thumbnail.toImageUrl())
        }
    }

    override fun getFavoriteSeries(): Flow<FavouriteScreenState<List<Series>>> {
        return wrapWithFavoriteScreenState{ dao.getFavoriteSeries() }
    }

    override suspend fun addFavouriteSeries(series: Series) {
        dao.insertFavouriteSeries(domainMappers.favoriteSeriesMapper.inverseMap(series))
    }

    override suspend fun deleteFavouriteSeries(seriesId: Int) {
        dao.deleteFavouriteSeries(seriesId)
    }

    override suspend fun clearFavoriteSeries() {
        dao.clearFavoriteSeries()
    }

    @OptIn(FlowPreview::class)
    private fun wrapWithFavoriteScreenState(function: () -> Flow<List<FavoriteEntity>>): Flow<FavouriteScreenState<List<Series>>> {
        return function().flatMapConcat{ list ->
            flow{
                emit(FavouriteScreenState.Loading)
                takeIf { list.isNotEmpty() }?.let {
                    val series = list.map(domainMappers.favoriteSeriesMapper::map)
                    emit(FavouriteScreenState.Success(series))
                } ?: run {
                    emit(FavouriteScreenState.Empty)
                }
            }
        }
    }


}