package com.shahad.app.marvelapp.data

import com.shahad.app.marvelapp.data.local.entities.CreatorEntity
import com.shahad.app.marvelapp.data.local.mappers.LocalMappers
import com.shahad.app.marvelapp.data.remote.response.CreatorDto
import com.shahad.app.marvelapp.data.repositories.CreatorsRepository
import com.shahad.app.marvelapp.domain.mappers.DomainMapper
import com.shahad.app.marvelapp.domain.models.Creator
import com.shahad.app.marvelapp.util.toImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeCreatorRepository: CreatorsRepository{

    @Inject
    lateinit var domainMapper: DomainMapper

    @Inject
    lateinit var localMapper: LocalMappers

    private var shouldReturnError = false

    private val remoteCreators = mutableMapOf(
        Pair(1,CreatorDto(id = 1, name = "c1", modified = "2020/2/1")),
        Pair(2, CreatorDto(id = 2, name = "c2", modified = "2020/2/1")),
    )

    private val localCreators = mutableMapOf<Int,CreatorEntity>()


    fun setReturnError(value: Boolean){
        shouldReturnError = value
    }

    override suspend fun getCreators(numberOfCreators: Int): Flow<HomeScreenState<List<Creator>>> {
        refreshCreators(numberOfCreators)
        return flow {
            emit(HomeScreenState.Success(localCreators.map { domainMapper.creatorMapper.map(it.value) }))
        }
    }

    override suspend fun refreshCreators(numberOfCreators: Int) {
        localCreators.putAll(remoteCreators.map { Pair(it.key,localMapper.creatorEntityMapper.map(it.value)) })
    }

    override fun searchCreator(keyWord: String): Flow<SearchScreenState<List<Creator>?>?> {
        val creators = remoteCreators.values.filter { it.name.matches(keyWord.toRegex()) }.map{
            Creator(it.id,it.name,it.thumbnail.toImageUrl())
        }
        return if(shouldReturnError){
            flow { SearchScreenState.Error("fake error") }
        }else {
            flow {
                emit(SearchScreenState.Success(creators))
            }
        }
    }

}