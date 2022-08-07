package com.shahad.app.repositories

import com.shahad.app.core.models.Series
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun Series.setIsFavourite(value: Boolean) =
    this.apply {
        isFavourite = value
    }

fun <T, U> Flow<List<T>>.convertTo(
    mapper: (T) -> U
): Flow<List<U>> =
    this.map { list ->
        list.map { entity ->
            mapper(entity)
        }
    }

