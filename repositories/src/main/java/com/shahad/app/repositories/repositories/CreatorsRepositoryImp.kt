package com.shahad.app.repositories.repositories


import com.shahad.app.data.local.MarvelDao
import com.shahad.app.data.mappers.LocalMappers
import com.shahad.app.data.remote.MarvelService
import com.shahad.app.data.toImageUrl
import com.shahad.app.repositories.mappers.DomainMapper
import com.shahad.app.repositories.convertTo
import com.shahad.app.core.models.Creator
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
            numberOfCreators
        ){body ->
            body?.data?.results?.map { creatorDto ->
                localMappers.creatorEntityMapper.map(creatorDto)
            }

        }
    }


    override  fun searchCreator(keyWord: String): Flow<List<Creator>?> {
        return wrapWithFlow(
            request = { api.getCharacters(searchKeyWord = keyWord) },
            mapper = {
                Creator(it.id,it.name,it.thumbnail.toImageUrl())
            }
        )

    }


}