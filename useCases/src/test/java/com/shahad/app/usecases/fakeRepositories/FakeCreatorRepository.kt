package com.shahad.app.usecases.fakeRepositories

import com.shahad.app.core.models.Creator
import com.shahad.app.data.local.entities.CreatorEntity
import com.shahad.app.data.mappers.*
import com.shahad.app.data.remote.response.CreatorDto
import com.shahad.app.repositories.mappers.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.shahad.app.data.toImageUrl
import com.shahad.app.repositories.repositories.CreatorsRepository

class FakeCreatorRepository: CreatorsRepository {


    var domainMapper: DomainMapper = DomainMapper(
        CharacterMapper(), SeriesMapper(),
        CreatorMapper(), FavoriteSeriesMapper()
    )

    var localMapper: LocalMappers = LocalMappers(
        CharacterEntityMapper(), SeriesEntityMapper(),
        CreatorEntityMapper()
    )

    private var shouldReturnError = false

    private val remoteCreators = mutableMapOf(
        Pair(1, CreatorDto(id = 1, name = "c1", modified = "2020/2/1")),
        Pair(2, CreatorDto(id = 2, name = "c2", modified = "2020/2/1")),
    )

    private val localCreators = mutableMapOf<Int, CreatorEntity>()


    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }


    fun updateRemoteCreators(){
        remoteCreators[3] = CreatorDto(id = 3, name = "c3", modified = "2020/3/3")
    }

    fun getSizeOfRemoteCreators() = remoteCreators.size

    override suspend fun getCreators(numberOfCreators: Int): Flow<List<Creator>> {
        refreshCreators(numberOfCreators)
        return flow {
            emit(localCreators.map { domainMapper.creatorMapper.map(it.value) })
        }
    }

    override suspend fun refreshCreators(numberOfCreators: Int) {
        takeUnless { shouldReturnError }?.let {
            localCreators.putAll(remoteCreators.map { Pair(it.key,localMapper.creatorEntityMapper.map(it.value)) })
        }
    }

    override fun searchCreator(keyWord: String): Flow<List<Creator>?> {
        return flow {
            if(shouldReturnError){
                emit(null)
            }else {
                val creators = remoteCreators.values.filter { it.name.contains(keyWord) }.map{
                    Creator(it.id,it.name,it.thumbnail.toImageUrl())
                }
                emit(creators)
            }
        }
    }

}