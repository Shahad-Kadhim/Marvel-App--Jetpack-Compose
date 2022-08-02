package com.shahad.app.marvelapp.util

import com.shahad.app.marvelapp.data.State
import com.shahad.app.marvelapp.data.remote.response.Thumbnail
import kotlinx.coroutines.flow.Flow
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

fun<T,U> Flow<State<List<T>?>?>.toModel(map: (T) -> U) =
    this.map {
        when(it){
            is State.Error-> State.Error(it.message)
            is State.Success -> State.Success(
                it.data?.map{t ->
                    map(t)
                }
            )
            State.Loading -> State.Loading
            null -> null
        }
    }
