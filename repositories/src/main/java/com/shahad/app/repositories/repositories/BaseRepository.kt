package com.shahad.app.repositories.repositories

import android.util.Log
import com.shahad.app.data.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.text.FieldPosition


internal interface BaseRepository {

    suspend fun <T, U> refreshDataBase(
        request: suspend (Int,Int) -> Response<T>,
        insertIntoDatabase: suspend (List<U>) -> Unit,
        numberOfResponse: Int,
        position: Int,
        mapper: suspend (T?) -> List<U>?,
    ) {
        try {
            request(numberOfResponse, position).also { response ->
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


    fun <T,U> wrapWithFlow(request: suspend () -> Response<BaseResponse<T>>, mapper: suspend (T) -> U): Flow<List<U>?> {
        return flow {
            try {
                emit(request().takeIf { it.isSuccessful }?.let {
                    it.body()?.data
                        ?.results?.map { remoteRespond ->
                            mapper(remoteRespond)
                        }
                })
            } catch (e: Exception) {
                emit(null)
                Log.i("RRI",e.message.toString())
            }
        }
    }

}
