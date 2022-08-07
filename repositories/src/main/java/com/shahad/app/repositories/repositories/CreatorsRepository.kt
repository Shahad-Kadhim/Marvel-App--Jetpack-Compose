package com.shahad.app.repositories.repositories

import com.shahad.app.core.models.Creator
import kotlinx.coroutines.flow.Flow

interface CreatorsRepository {
    suspend fun getCreators(numberOfCreators: Int = 10): Flow<List<Creator>>
    suspend fun refreshCreators(numberOfCreators: Int)
    fun searchCreator(keyWord: String): Flow<List<Creator>?>
}