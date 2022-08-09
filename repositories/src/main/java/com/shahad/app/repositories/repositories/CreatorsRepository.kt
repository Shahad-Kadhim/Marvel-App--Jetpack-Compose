package com.shahad.app.repositories.repositories

import androidx.paging.PagingData
import com.shahad.app.core.models.Creator
import kotlinx.coroutines.flow.Flow

interface CreatorsRepository {
    suspend fun getCreators(numberOfCreators: Int = 10): Flow<List<Creator>>
    suspend fun refreshCreators(numberOfCreators: Int)
    fun searchCreatorWithName(keyWord: String): Flow<PagingData<Creator>>
}