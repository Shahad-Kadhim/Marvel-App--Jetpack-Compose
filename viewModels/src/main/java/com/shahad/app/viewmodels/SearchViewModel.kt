package com.shahad.app.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shahad.app.core.FilterType
import com.shahad.app.core.SearchScreenState
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Creator
import com.shahad.app.core.models.Series
import com.shahad.app.usecases.SearchCharacterUseCase
import com.shahad.app.usecases.SearchCreatorUseCase
import com.shahad.app.usecases.SearchSeriesUseCase
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

    val characters = MediatorLiveData<PagingData<Character>>().apply {
        addSource(search.asLiveData()){ characterName ->
            takeIf { filterType.value == FilterType.CHARACTER && characterName.isNotBlank() }?.let {
                collect(searchCharacterUseCase.invoke(characterName).cachedIn(viewModelScope))
            }
            if (characterName.isNullOrBlank()){
                this.value = PagingData.empty<Character>()
            }
        }
        addSource(filterType.asLiveData()){ currentFilterType ->
            takeIf { currentFilterType == FilterType.CHARACTER && search.value.isNotBlank()}?.let {
                collect(searchCharacterUseCase.invoke(search.value).cachedIn(viewModelScope))
            }
            if (search.value.isBlank()){
                this.value = PagingData.empty<Character>()
            }
        }
    }.asFlow()

    val creator = MediatorLiveData<SearchScreenState<List<Creator>?>?>().apply {
        addSource(search.asLiveData()){ creatorName ->
            takeIf { filterType.value == FilterType.CREATOR && creatorName.isNotBlank() }?.let {
                collect(searchCreatorUseCase.invoke(creatorName))
            }
            emitNullIfEmpty(this,creatorName)
        }
        addSource(filterType.asLiveData()){ currentFilterType ->
            takeIf { currentFilterType == FilterType.CREATOR  && search.value.isNotBlank()}?.let {
                 collect(searchCreatorUseCase.invoke(search.value))
            }
            emitNullIfEmpty(this,search.value)
        }
    }

    val series = MediatorLiveData<SearchScreenState<List<Series>?>?>().apply {
        addSource(search.asLiveData()){ seriesTitle ->
            takeIf { filterType.value == FilterType.SERIES && seriesTitle.isNotBlank()}?.let {
                collect(searchSeriesUseCase.invoke(seriesTitle))
            }
            emitNullIfEmpty(this,seriesTitle)
        }
        addSource(filterType.asLiveData()){ currentFilterType ->
            takeIf { currentFilterType == FilterType.SERIES && search.value.isNotBlank()}?.let {
                collect(searchSeriesUseCase.invoke(search.value))
            }
            emitNullIfEmpty(this,search.value)
        }
    }

    private fun <T> MediatorLiveData<T>.collect(flow: Flow<T>){
        viewModelScope.launch {
            flow.collect{
                this@collect.postValue(it)
            }
        }
    }

    private fun  <T> emitNullIfEmpty(liveData: MediatorLiveData<T?>, keyWord: String){
        keyWord.takeIf { it.isEmpty() }?.let {
            liveData.postValue(null)
        }
    }
}