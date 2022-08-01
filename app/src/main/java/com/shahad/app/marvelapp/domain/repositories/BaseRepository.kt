package com.shahad.app.marvelapp.domain.repositories

import android.util.Log
import com.shahad.app.marvelapp.data.State
import com.shahad.app.marvelapp.data.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.lang.Exception

interface BaseRepository {

    suspend fun <T, U> refreshWrapper(
        request: suspend (Int) -> Response<T>,
        insertIntoDatabase: suspend (List<U>) -> Unit,
        numberOfResponse: Int,
        mapper: (T?) -> List<U>?,
    ) {
        try {
            request(numberOfResponse).also {
                if (it.isSuccessful) {
                    mapper(it.body())?.let { list ->
                        insertIntoDatabase(list)
                    }
                }
            }
        } catch (exception: Exception) {
            Log.i("MARVEL", "no connection cant update data")
            Log.i("MARVEL",exception.message.toString())
        }
    }

    fun <T> wrapWithFlow(function: suspend () -> Response<BaseResponse<T>>): Flow<State<List<T>?>> {
        return flow {
            emit(State.Loading)
            try {
                emit(checkIsSuccessful(function()))
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }
    }


    private fun <T> checkIsSuccessful(response: Response<BaseResponse<T>>): State<List<T>?> =
        if (response.isSuccessful) {
            State.Success(response.body()?.data?.results )
        } else {
            State.Error(response.message())
        }

    fun <T, U> wrapper(
        data: Flow<List<T>>,
        mapper: (T) -> U
    ): Flow<List<U>> =
        data.map { list ->
            list.map { entity ->
                mapper(entity)
            }
        }


}
