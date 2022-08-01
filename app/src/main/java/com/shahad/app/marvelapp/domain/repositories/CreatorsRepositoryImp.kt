package com.shahad.app.marvelapp.domain.repositories

import com.shahad.app.marvelapp.data.local.MarvelDao
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.remote.MarvelService
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.models.Creator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatorsRepositoryImp @Inject constructor(
    private val api: MarvelService,
    private val dao: MarvelDao,
    private val domainMappers: DomainMapper,
    private val localMappers: LocalMappers
): CreatorsRepository, BaseRepository {
    override suspend fun getCreators(numberOfCreators: Int): Flow<List<Creator>> {
        refreshCreators(numberOfCreators)
        return wrapper(
            dao.getCreator(),
            domainMappers.creatorMapper::map
        )
    }

    override suspend fun refreshCreators(numberOfCreators: Int) {
        refreshWrapper(
            api::getCreators,
            dao::insertCreator,
            numberOfCreators
        ){body ->
            body?.data?.results?.map { creatorDto ->
                localMappers.creatorEntityMapper.map(creatorDto)
            }

        }
    }

}