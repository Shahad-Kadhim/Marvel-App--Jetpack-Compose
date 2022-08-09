package com.shahad.app.repositories.repositories


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shahad.app.core.models.Creator
import com.shahad.app.data.remote.MarvelService
import com.shahad.app.repositories.CreatorSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatorsRepositoryImp @Inject constructor(
    private val api: MarvelService,
): CreatorsRepository, BaseRepository {


    override fun searchCreatorWithName(keyWord: String): Flow<PagingData<Creator>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CreatorSource(api, keyWord ) }
        ).flow
    }


}