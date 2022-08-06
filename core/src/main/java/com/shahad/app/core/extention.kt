package com.shahad.app.core

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.math.BigInteger
import java.security.MessageDigest

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
