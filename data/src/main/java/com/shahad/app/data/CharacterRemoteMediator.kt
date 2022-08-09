package com.shahad.app.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shahad.app.data.local.MarvelDao
import com.shahad.app.data.local.MarvelDatabase
import com.shahad.app.data.local.entities.CharacterEntity
import com.shahad.app.data.mappers.LocalMappers
import com.shahad.app.data.remote.MarvelService

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val searchKeyword: String?,
    private val api: MarvelService,
    private val database: MarvelDatabase,
    private val dao: MarvelDao,
    private val localMappers: LocalMappers,
): RemoteMediator<Int,CharacterEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH ->{
                STARTING_INDEX
            }
            LoadType.PREPEND -> {
                 return MediatorResult.Success(true)
            }
            LoadType.APPEND -> {
                if(state.pages.size * SIZE_LOAD <= dao.getSizeOfCharacter()){
                    dao.getSizeOfCharacter() + SIZE_LOAD
                }else
                    return MediatorResult.Success(true)

            }
        }

            try{
                val response =api.getCharacters(
                    SIZE_LOAD,
                    page,
                    searchKeyword
                )

                val characters =response.body()?.data?.results
                val endOfPagingReached = characters.isNullOrEmpty()
                database.withTransaction {
                    if(loadType == LoadType.REFRESH){
                        dao.clearAllCharacter()
                    }
                    characters?.map(localMappers.characterEntityMapper::map)
                        ?.let { dao.insertCharacters(it) }
                }
                return  MediatorResult.Success(endOfPagingReached)
            }catch (e: Exception){
                return MediatorResult.Error(e)
            }
    }

    companion object{
        const val STARTING_INDEX = 0
        const val SIZE_LOAD =10
    }

}