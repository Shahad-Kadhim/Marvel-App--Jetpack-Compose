package com.shahad.app.marvelapp.ui.search

import androidx.lifecycle.*
import com.shahad.app.marvelapp.data.SearchScreenState
import com.shahad.app.marvelapp.domain.models.Character
import com.shahad.app.marvelapp.domain.models.Creator
import com.shahad.app.marvelapp.domain.models.Series
import com.shahad.app.marvelapp.domain.useCases.SearchCharacterUseCase
import com.shahad.app.marvelapp.domain.useCases.SearchCreatorUseCase
import com.shahad.app.marvelapp.domain.useCases.SearchSeriesUseCase
import com.shahad.app.marvelapp.util.FilterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCharacterUseCase: SearchCharacterUseCase,
    private val searchSeriesUseCase: SearchSeriesUseCase,
    private val searchCreatorUseCase: SearchCreatorUseCase
): ViewModel() {

    val search = MutableStateFlow<String>("")
    val filterType = MutableStateFlow<FilterType>(FilterType.CHARACTER)
    val isFiltersVisible = MutableStateFlow(false)

    val characters = MediatorLiveData<SearchScreenState<List<Character>?>?>().apply {
        addSource(search.asLiveData()){ characterName ->
            takeIf { filterType.value == FilterType.CHARACTER && characterName.isNotBlank() }?.let {
                collect(searchCharacterUseCase.invoke(characterName))
            }
        }
        addSource(filterType.asLiveData()){ currentFilterType ->
            takeIf { currentFilterType == FilterType.CHARACTER && search.value.isNotBlank()}?.let {
                collect(searchCharacterUseCase.invoke(search.value))
            }
        }
    }

    val creator = MediatorLiveData<SearchScreenState<List<Creator>?>?>().apply {
        addSource(search.asLiveData()){ creatorName ->
            takeIf { filterType.value == FilterType.CREATOR && creatorName.isNotBlank() }?.let {
                collect(searchCreatorUseCase.invoke(creatorName))
            }
        }
        addSource(filterType.asLiveData()){ currentFilterType ->
            takeIf { currentFilterType == FilterType.CREATOR  && search.value.isNotBlank()}?.let {
                 collect(searchCreatorUseCase.invoke(search.value))
            }
        }
    }

    val series = MediatorLiveData<SearchScreenState<List<Series>?>?>().apply {
        addSource(search.asLiveData()){ seriesTitle ->
            takeIf { filterType.value == FilterType.SERIES && seriesTitle.isNotBlank()}?.let {
                collect(searchSeriesUseCase.invoke(seriesTitle))
            }
        }
        addSource(filterType.asLiveData()){ currentFilterType ->
            takeIf { currentFilterType == FilterType.SERIES && search.value.isNotBlank()}?.let {
                collect(searchSeriesUseCase.invoke(search.value))
            }
        }
    }

    private fun <T> MediatorLiveData<T>.collect(flow: Flow<T>){
        viewModelScope.launch {
            flow.collect{
                this@collect.postValue(it)
            }
        }
    }
}