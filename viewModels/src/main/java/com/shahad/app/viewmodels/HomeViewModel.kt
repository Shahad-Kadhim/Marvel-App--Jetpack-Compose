package com.shahad.app.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shahad.app.core.HomeScreenState
import com.shahad.app.core.models.Series
import com.shahad.app.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
    private val seriesUseCase: GetSeriesUseCase,
    private val addSeriesToFavoriteUseCase: AddSeriesToFavoriteUseCase,
    private val deleteSeriesFromFavoriteUseCase: DeleteSeriesFromFavoriteUseCase
): ViewModel() {

    val series = MutableLiveData<HomeScreenState<List<Series>?>?>()
    val characters = charactersUseCase.invoke().cachedIn(viewModelScope)

    init {
        collectSeries()
    }

     private fun collectSeries() {
        viewModelScope.launch{
            seriesUseCase.invoke(20).collect {
                series.value = it
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