package com.shahad.app.usecases

import com.shahad.app.core.DetailsScreenState
import com.shahad.app.core.FavouriteScreenState
import com.shahad.app.core.SearchScreenState
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Series
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take


@OptIn(FlowPreview::class)
fun Flow<List<Series>>.mapToFavouriteState(): Flow<FavouriteScreenState<List<Series>>> {
    return this.flatMapConcat{ series ->
        flow {
            emit(FavouriteScreenState.Loading)
            takeIf { series.isNotEmpty() }?.let {
                emit(FavouriteScreenState.Success(series))
            } ?: run {
                emit(FavouriteScreenState.Empty)
            }
        }
    }
}



@OptIn(FlowPreview::class)
fun <T> Flow<List<T>?>.mapToSearchState(): Flow<SearchScreenState<List<T>?>> {
    return this.flatMapConcat { list ->
        flow {
            emit(SearchScreenState.Loading)
            list?.let {
                it.takeIf { it.isNotEmpty() }?.let {
                    emit(SearchScreenState.Success(it))
                } ?: run {
                    emit(SearchScreenState.Empty)
                }
            } ?: run {
                emit(SearchScreenState.Error("no Connection"))
            }
        }
    }
}


@OptIn(FlowPreview::class)
fun <T> Flow<T?>.mapToDetailsState(): Flow<DetailsScreenState<T?>> {
    return this.flatMapConcat { model ->
        flow {
            emit(DetailsScreenState.Loading)
            model?.let {
                emit(DetailsScreenState.Success(model))
            } ?: run {
                emit(DetailsScreenState.Error("no Connection"))
            }
        }
    }
}
