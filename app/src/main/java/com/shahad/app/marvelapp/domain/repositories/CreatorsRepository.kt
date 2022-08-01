package com.shahad.app.marvelapp.domain.repositories

import com.shahad.app.marvelapp.domain.models.Creator
import kotlinx.coroutines.flow.Flow

interface CreatorsRepository {
    suspend fun getCreators(numberOfCreators: Int = 10): Flow<List<Creator>>
    suspend fun refreshCreators(numberOfCreators: Int)
}