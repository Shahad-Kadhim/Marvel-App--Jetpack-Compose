package com.shahad.app.repositories.repositories

import androidx.paging.PagingData
import com.shahad.app.core.models.Creator
import kotlinx.coroutines.flow.Flow

interface CreatorsRepository {
    fun searchCreatorWithName(keyWord: String): Flow<PagingData<Creator>>
}