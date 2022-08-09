package com.shahad.app.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahad.app.core.FilterType
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Creator
import com.shahad.app.core.models.Series
import com.shahad.app.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCharacterUseCase: SearchCharacterUseCase,
    private val searchSeriesUseCase: SearchSeriesUseCase,
    private val searchCreatorUseCase: SearchCreatorUseCase,
    private val addSeriesToFavoriteUseCase: AddSeriesToFavoriteUseCase,
    private val deleteSeriesFromFavoriteUseCase: DeleteSeriesFromFavoriteUseCase
): ViewModel() {

    val search = MutableStateFlow<String>("")
    val filterType = MutableStateFlow<FilterType>(FilterType.CHARACTER)
    val isFiltersVisible = MutableStateFlow(false)

    val characters = MediatorLiveData<PagingData<Character>>().apply {
        addSource(search.asLiveData()){ characterName ->
            takeIf { filterType.value == FilterType.CHARACTER && characterName.isNotBlank() }?.let {
                collect(searchCharacterUseCase.invoke(characterName).cachedIn(viewModelScope))
            }
        }
        addSource(filterType.asLiveData()){ currentFilterType ->
            takeIf { currentFilterType == FilterType.CHARACTER && search.value.isNotBlank()}?.let {
                collect(searchCharacterUseCase.invoke(search.value).cachedIn(viewModelScope))
            }
        }
    }.asFlow()

    val creator = MediatorLiveData<PagingData<Creator>>().apply {
        addSource(search.asLiveData()){ creatorName ->
            takeIf { filterType.value == FilterType.CREATOR && creatorName.isNotBlank() }?.let {
                collect(searchCreatorUseCase.invoke(creatorName).cachedIn(viewModelScope))
            }
        }
        addSource(filterType.asLiveData()){ currentFilterType ->
            takeIf { currentFilterType == FilterType.CREATOR  && search.value.isNotBlank()}?.let {
                 collect(searchCreatorUseCase.invoke(search.value).cachedIn(viewModelScope))
            }
        }
    }.asFlow()

    val series = MediatorLiveData<PagingData<Series>>().apply {
        addSource(search.asLiveData()){ seriesTitle ->
            takeIf { filterType.value == FilterType.SERIES && seriesTitle.isNotBlank()}?.let {
                collect(searchSeriesUseCase.invoke(seriesTitle).cachedIn(viewModelScope))
            }
        }
        addSource(filterType.asLiveData()){ currentFilterType ->
            takeIf { currentFilterType == FilterType.SERIES && search.value.isNotBlank()}?.let {
                collect(searchSeriesUseCase.invoke(search.value).cachedIn(viewModelScope))
            }
        }
    }.asFlow()

    private fun <T> MediatorLiveData<T>.collect(flow: Flow<T>){
        viewModelScope.launch {
            flow.collect{
                this@collect.postValue(it)
            }
        }
    }
    fun addSeriesToFavorite(series: Series){
        viewModelScope.launch {
            addSeriesToFavoriteUseCase.invoke(series)
        }
    }

    fun deleteSeriesToFavorite(seriesId: Long){
        viewModelScope.launch {
            deleteSeriesFromFavoriteUseCase.invoke(seriesId)
        }
    }
}