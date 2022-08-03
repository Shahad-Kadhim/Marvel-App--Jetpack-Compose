package com.shahad.app.marvelapp.data.repositories

import com.shahad.app.marvelapp.data.HomeScreenState
import com.shahad.app.marvelapp.data.SearchScreenState
import com.shahad.app.marvelapp.data.local.MarvelDao
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.remote.MarvelService
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.models.Creator
import com.shahad.app.marvelapp.util.convertTo
import com.shahad.app.marvelapp.util.toImageUrl
import com.shahad.app.marvelapp.util.toModel
import com.shahad.app.marvelapp.util.wrapWithHomeStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatorsRepositoryImp @Inject constructor(
    private val api: MarvelService,
    private val dao: MarvelDao,
    private val domainMappers: DomainMapper,
    private val localMappers: LocalMappers
): CreatorsRepository, BaseRepository {
    override suspend fun getCreators(numberOfCreators: Int): Flow<HomeScreenState<List<Creator>>> {
        refreshCreators(numberOfCreators)
        return dao.getCreator()
            .convertTo(domainMappers.creatorMapper::map)
            .wrapWithHomeStates()
    }

    override suspend fun refreshCreators(numberOfCreators: Int) {
        refreshDataBase(
            api::getCreators,
            dao::insertCreator,
            numberOfCreators
        ){body ->
            body?.data?.results?.map { creatorDto ->
                localMappers.creatorEntityMapper.map(creatorDto)
            }

        }
    }


    override fun searchCreator(keyWord: String): Flow<SearchScreenState<List<Creator>?>?> {
        return wrapWithFlowOfSearchState { api.getCreators(searchKeyWord = keyWord) }.toModel {
            Creator(it.id,it.name,it.thumbnail.toImageUrl())
        }
    }


}