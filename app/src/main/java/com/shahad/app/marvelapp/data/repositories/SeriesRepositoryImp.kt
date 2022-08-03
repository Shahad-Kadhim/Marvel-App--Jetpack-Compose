package com.shahad.app.marvelapp.data.repositories

import com.shahad.app.marvelapp.data.HomeScreenState
import com.shahad.app.marvelapp.data.SearchScreenState
import com.shahad.app.marvelapp.data.local.MarvelDao
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.remote.MarvelService
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.models.Series
import com.shahad.app.marvelapp.util.convertTo
import com.shahad.app.marvelapp.util.toImageUrl
import com.shahad.app.marvelapp.util.toModel
import com.shahad.app.marvelapp.util.wrapWithHomeStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesRepositoryImp @Inject constructor(
    private val api: MarvelService,
    private val dao: MarvelDao,
    private val domainMappers: DomainMapper,
    private val localMappers: LocalMappers
): SeriesRepository , BaseRepository {
    override suspend fun getSeries(numberOfSeries: Int): Flow<HomeScreenState<List<Series>>> {
        refreshSeries(numberOfSeries)
        return dao.getSeries()
            .convertTo(domainMappers.seriesMapper::map)
            .wrapWithHomeStates()
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

}