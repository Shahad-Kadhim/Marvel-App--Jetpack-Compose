package com.shahad.app.marvelapp.domain.repositories

import com.shahad.app.marvelapp.data.local.MarvelDao
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.remote.MarvelService
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.models.Series
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeriesRepositoryImp @Inject constructor(
    private val api: MarvelService,
    private val dao: MarvelDao,
    private val domainMappers: DomainMapper,
    private val localMappers: LocalMappers
): SeriesRepository , BaseRepository {
    override suspend fun getSeries(numberOfSeries: Int): Flow<List<Series>> {
        refreshSeries(numberOfSeries)
        return wrapper(
            dao.getSeries(),
            domainMappers.seriesMapper::map
        )
    }

    override suspend fun refreshSeries(numberOfSeries: Int) {
        refreshWrapper(
            api::getSeries,
            dao::insertSeries,
            numberOfSeries,
        ){ body ->
            body?.data?.results?.map { seriesDto ->
                localMappers.seriesEntityMapper.map(seriesDto)
            }
        }
    }

}