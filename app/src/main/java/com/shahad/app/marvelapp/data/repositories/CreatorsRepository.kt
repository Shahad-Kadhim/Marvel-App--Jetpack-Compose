package com.shahad.app.marvelapp.data.repositories

import com.shahad.app.marvelapp.data.SearchScreenState
import com.shahad.app.marvelapp.domain.models.Creator
import kotlinx.coroutines.flow.Flow

interface CreatorsRepository {
    suspend fun getCreators(numberOfCreators: Int = 10): Flow<List<Creator>>
    suspend fun refreshCreators(numberOfCreators: Int)
    fun searchCreator(keyWord: String): Flow<SearchScreenState<List<Creator>?>?>
}