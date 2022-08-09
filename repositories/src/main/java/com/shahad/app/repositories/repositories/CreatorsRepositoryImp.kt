package com.shahad.app.repositories.repositories


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shahad.app.data.local.MarvelDao
import com.shahad.app.data.mappers.LocalMappers
import com.shahad.app.data.remote.MarvelService
import com.shahad.app.data.toImageUrl
import com.shahad.app.repositories.mappers.DomainMapper
import com.shahad.app.repositories.convertTo
import com.shahad.app.core.models.Creator
import com.shahad.app.repositories.CharacterSource
import com.shahad.app.repositories.CreatorSource
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
        return dao.getCreator()
            .convertTo(domainMappers.creatorMapper::map)
    }

    override suspend fun refreshCreators(numberOfCreators: Int) {
        refreshDataBase(
            api::getCreators,
            dao::insertCreator,
            0 ,
            numberOfCreators
        ){body ->
            body?.data?.results?.map { creatorDto ->
                localMappers.creatorEntityMapper.map(creatorDto)
            }

        }
    }

    override fun searchCreatorWithName(keyWord: String): Flow<PagingData<Creator>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CreatorSource(api, keyWord ) }
        ).flow
    }


}