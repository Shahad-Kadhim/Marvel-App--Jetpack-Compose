package com.shahad.app.marvelapp.util

import com.shahad.app.marvelapp.data.HomeScreenState
import com.shahad.app.marvelapp.data.SearchScreenState
import com.shahad.app.marvelapp.data.local.entities.FavoriteEntity
import com.shahad.app.marvelapp.data.remote.response.Thumbnail
import com.shahad.app.marvelapp.domain.models.Series
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String {
    with(MessageDigest.getInstance("MD5")){
        return BigInteger(1, digest(toByteArray())).toString(16).padStart(32, '0')
    }
}

fun Thumbnail?.toImageUrl()=
    "${this?.path}.${this?.extension}".replaceHttpWithHttps()


fun String.replaceHttpWithHttps() =
    this.replace("http","https")

fun<T,U> Flow<SearchScreenState<List<T>?>?>.toModel(map: (T) -> U) =
    this.map {
        when(it){
            is SearchScreenState.Error-> SearchScreenState.Error(it.message)
            is SearchScreenState.Success -> SearchScreenState.Success(
                it.data?.map{t ->
                    map(t)
                }
            )
            SearchScreenState.Loading -> SearchScreenState.Loading
            SearchScreenState.Empty -> SearchScreenState.Empty
            null -> null
        }
    }


fun <T, U> Flow<List<T>>.convertTo(
    mapper: (T) -> U
): Flow<List<U>> =
    this.map { list ->
        list.map { entity ->
            mapper(entity)
        }
    }


@OptIn(FlowPreview::class)
fun<T> Flow<List<T>>.wrapWithHomeStates(): Flow<HomeScreenState<List<T>>>{
    return this.flatMapConcat {
        flow{
            emit(HomeScreenState.Loading)
            it.takeIf { it.isNotEmpty() }?.let {
                emit(HomeScreenState.Success(it))
            } ?: run {
                emit(HomeScreenState.Empty)
            }

        }
    }
}

fun <T> HomeScreenState<T>.showIfSuccess(show:  (HomeScreenState.Success<T>) -> Unit){
    when(this) {
        is HomeScreenState.Success -> show(this)
        else -> {}
    }
}
