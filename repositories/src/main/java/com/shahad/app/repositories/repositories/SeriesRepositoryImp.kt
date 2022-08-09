package com.shahad.app.repositories.repositories

import com.shahad.app.data.local.MarvelDao
import com.shahad.app.data.mappers.LocalMappers
import com.shahad.app.data.remote.MarvelService
import com.shahad.app.data.toImageUrl
import com.shahad.app.repositories.mappers.DomainMapper
import com.shahad.app.core.models.Series
import com.shahad.app.repositories.convertTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
            .convertTo(domainMappers.seriesMapper::map).map {
                it.onEach {series ->  if (ifSeriesFavourite(series.id))  series.isFavourite = true }
            }
    }

    override suspend fun refreshSeries(numberOfSeries: Int) {
        refreshDataBase(
            api::getSeries,
            dao::insertSeries,
            numberOfSeries,
            0,
        ){ body ->
            body?.data?.results?.map(localMappers.seriesEntityMapper::map)
        }
    }

    override fun searchSeries(keyWord: String): Flow<List<Series>?> {
        return wrapWithFlow(
            request = { api.getSeries(searchKeyWord = keyWord) },
            mapper = { Series(it.id,it.rating,it.title ?: "",it.thumbnail.toImageUrl(),ifSeriesFavourite(it.id)) }
        )
    }

    override fun getFavoriteSeries(): Flow<List<Series>> {
        return dao.getFavoriteSeries().convertTo(domainMappers.seriesMapper::map)
    }

    override suspend fun addFavouriteSeries(series: Series) {
        dao.insertFavouriteSeries(domainMappers.seriesMapper.inverseMap(series.apply { isFavourite = true }))
    }

    override suspend fun deleteFavouriteSeries(seriesId: Int) {
        dao.deleteFavouriteSeries(seriesId)
    }

    override suspend fun clearFavoriteSeries() {
        dao.clearFavoriteSeries()
    }

    suspend fun ifSeriesFavourite(seriesId: Int) =
        dao.ifSeriesFavourite(seriesId)


}