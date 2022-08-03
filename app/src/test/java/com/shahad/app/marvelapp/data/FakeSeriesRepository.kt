package com.shahad.app.marvelapp.data

import com.shahad.app.marvelapp.data.local.entities.SeriesEntity
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.remote.response.SeriesDto
import com.shahad.app.marvelapp.data.repositories.SeriesRepository
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.models.Series
import com.shahad.app.marvelapp.util.toImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeSeriesRepository: SeriesRepository{

    @Inject
    lateinit var domainMapper: DomainMapper

    @Inject
    lateinit var localMapper: LocalMappers

    private var shouldReturnError = false

    private val remoteSeries = mutableMapOf(
        Pair(1,SeriesDto(id = 1, title = "s1", description = "d1", modified = "2020/2/1")),
        Pair(2,SeriesDto(id = 2, title = "s2", description = "d2", modified = "2020/2/1")),
    )

    private val localSeries = mutableMapOf<Int,SeriesEntity>()


    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }

    override suspend fun getSeries(numberOfSeries: Int): Flow<HomeScreenState<List<Series>>> {
        refreshSeries(numberOfSeries)
        return flow {
            emit(HomeScreenState.Success(localSeries.map { domainMapper.seriesMapper.map(it.value) }))
        }
    }

    override suspend fun refreshSeries(numberOfSeries: Int) {
        localSeries.putAll(remoteSeries.map { Pair(it.key,localMapper.seriesEntityMapper.map(it.value)) })
    }

    override fun searchSeries(keyWord: String): Flow<SearchScreenState<List<Series>?>?> {
        val series = remoteSeries.values.filter { it.title.matches(keyWord.toRegex()) }.map{
            Series(id = it.id, title = it.title, imageUrl = it.thumbnail.toImageUrl(), rating = "5")
        }
        return if(shouldReturnError){
            flow { SearchScreenState.Error("fake error") }
        }else {
            flow {
                emit(SearchScreenState.Success(series))
            }
        }
    }

}