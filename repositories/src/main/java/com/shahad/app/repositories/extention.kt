package com.shahad.app.repositories

import com.shahad.app.core.SearchScreenState
import com.shahad.app.core.models.Series
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun Series.setIsFavourite(value: Boolean) =
    this.apply {
        isFavourite = value
    }



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

