package com.shahad.app.usecases.fakeRepositories

import androidx.paging.PagingData
import com.shahad.app.core.models.Creator
import com.shahad.app.data.local.entities.CreatorEntity
import com.shahad.app.data.mappers.*
import com.shahad.app.data.remote.response.CreatorDto
import com.shahad.app.repositories.mappers.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.shahad.app.data.toImageUrl
import com.shahad.app.repositories.repositories.CreatorsRepository
import com.shahad.app.repositories.toCreator

class FakeCreatorRepository: CreatorsRepository {


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

    override fun searchCreatorWithName(keyWord: String): Flow<PagingData<Creator>> {
        return flow {
           emit(PagingData.from(remoteCreators.values.filter { it.name.contains(keyWord) }.map { it.toCreator() }))
        }
    }


}