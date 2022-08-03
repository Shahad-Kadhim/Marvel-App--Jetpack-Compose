package com.shahad.app.marvelapp.data.repositories

import android.util.Log
import com.shahad.app.marvelapp.data.SearchScreenState
import com.shahad.app.marvelapp.data.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


interface BaseRepository {

    suspend fun <T, U> refreshDataBase(
        request: suspend (Int) -> Response<T>,
        insertIntoDatabase: suspend (List<U>) -> Unit,
        numberOfResponse: Int,
        mapper: (T?) -> List<U>?,
    ) {
        try {
            request(numberOfResponse).also { response ->
                if (response.isSuccessful) {
                    val entities = mapper(response.body())
                    entities?.let { it ->
                        insertIntoDatabase(it)
                    }
                }
            }
        } catch (exception: Exception) {
            Log.i("MARVEL", "no connection cant update data")
        }
    }

    fun <T> wrapWithFlowOfSearchState(function: suspend () -> Response<BaseResponse<T>>): Flow<SearchScreenState<List<T>?>> {
        return flow {
            emit(SearchScreenState.Loading)
            try {
                emit(checkIsSuccessful(function()))
            } catch (e: Exception) {
                emit(SearchScreenState.Error(e.message.toString()))
            }
        }
    }


    private fun <T> checkIsSuccessful(response: Response<BaseResponse<T>>): SearchScreenState<List<T>?> =
        if (response.isSuccessful) {
            response.body()?.data
                ?.results
                ?.takeIf { it.isNotEmpty()}
                ?.let {
                    SearchScreenState.Success(it)
                } ?: SearchScreenState.Empty
        } else {
            SearchScreenState.Error(response.message())
        }

}
