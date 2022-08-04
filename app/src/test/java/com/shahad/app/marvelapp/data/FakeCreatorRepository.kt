package com.shahad.app.marvelapp.data

import com.shahad.app.marvelapp.data.local.entities.CreatorEntity
import com.shahad.app.marvelapp.data.local.mappers.CharacterEntityMapper
import com.shahad.app.marvelapp.data.local.mappers.CreatorEntityMapper
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.local.mappers.SeriesEntityMapper
import com.shahad.app.marvelapp.data.remote.response.CreatorDto
import com.shahad.app.marvelapp.data.repositories.CreatorsRepository
import com.shahad.app.marvelapp.domain.mappers.CharacterMapper
import com.shahad.app.marvelapp.domain.mappers.CreatorMapper
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.mappers.SeriesMapper
import com.shahad.app.marvelapp.domain.models.Creator
import com.shahad.app.marvelapp.util.toImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCreatorRepository: CreatorsRepository{


    var domainMapper: DomainMapper = DomainMapper(
        CharacterMapper(), SeriesMapper(),
        CreatorMapper()
    )

    var localMapper: LocalMappers = LocalMappers(
        CharacterEntityMapper(), SeriesEntityMapper(),
        CreatorEntityMapper()
    )

    private var shouldReturnError = false

    private val remoteCreators = mutableMapOf(
        Pair(1,CreatorDto(id = 1, name = "c1", modified = "2020/2/1")),
        Pair(2, CreatorDto(id = 2, name = "c2", modified = "2020/2/1")),
    )

    private val localCreators = mutableMapOf<Int,CreatorEntity>()


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

    override fun searchCreator(keyWord: String): Flow<SearchScreenState<List<Creator>?>?> {
        return flow {
            emit(SearchScreenState.Loading)
            val creators = remoteCreators.values.filter { it.name.contains(keyWord) }.map{
                Creator(it.id,it.name,it.thumbnail.toImageUrl())
            }
            if(shouldReturnError){
                emit(SearchScreenState.Error("fake error"))
            }else {
                takeIf { creators.isNotEmpty() }?.let {
                    emit(SearchScreenState.Success(creators))
                } ?: run {
                    emit(SearchScreenState.Empty)
                }
            }
        }
    }

}