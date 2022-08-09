package com.shahad.app.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shahad.app.core.models.Character
import com.shahad.app.data.remote.MarvelService
import com.shahad.app.data.toImageUrl

class CharacterSource(
    private val api: MarvelService,
    private val searchKeyword: String?,
): PagingSource<Int,Character>(){
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.plus(10)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val nextPosition =  params.key ?: FIRST_POSITION_ITEM
        return try {
            val response = api.getCharacters(10,nextPosition,searchKeyword)
            response.body()?.data?.results.takeUnless { it.isNullOrEmpty() }?.let {
                LoadResult.Page(
                    data = it.map{
                          Character(it.id,it.name,it.thumbnail.toImageUrl(),it.description ?: "")
                    },
                    prevKey = if(nextPosition == FIRST_POSITION_ITEM) null else nextPosition - LOAD_SIZE,
                    nextKey = nextPosition + LOAD_SIZE
                )
            } ?: LoadResult.Error(Exception("no Data"))
        }catch (e: Exception){
            LoadResult.Error(e)
        }

    }

    companion object{
        const val FIRST_POSITION_ITEM = 0
        const val LOAD_SIZE = 10
    }
}