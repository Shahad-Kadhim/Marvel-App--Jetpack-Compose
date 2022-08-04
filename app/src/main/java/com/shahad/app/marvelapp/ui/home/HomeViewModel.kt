package com.shahad.app.marvelapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahad.app.marvelapp.data.HomeScreenState
import com.shahad.app.marvelapp.domain.models.Character
import com.shahad.app.marvelapp.domain.models.Creator
import com.shahad.app.marvelapp.domain.models.Series
import com.shahad.app.marvelapp.domain.useCases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
    private val seriesUseCase: GetSeriesUseCase,
    private val creatorsUseCase: GetCreatorsUseCase,
    private val addSeriesToFavoriteUseCase: AddSeriesToFavoriteUseCase,
    private val deleteSeriesFromFavoriteUseCase: DeleteSeriesFromFavoriteUseCase
): ViewModel() {

    val series = MutableLiveData<HomeScreenState<List<Series>?>?>()
    val creators = MutableLiveData<HomeScreenState<List<Creator>?>?>()
    val characters = MutableLiveData<HomeScreenState<List<Character>?>?>()

    init {
        collectSeries()
        collectCreators()
        collectCharacter()
    }

    private fun collectCreators() {
        viewModelScope.launch {
            creatorsUseCase.invoke(10).collect {
                    creators.value = it
                }
        }
    }

    private fun collectSeries() {
        viewModelScope.launch{
            seriesUseCase.invoke(10).collect {
                series.value = it
            }
        }
    }

    private fun collectCharacter(){
        viewModelScope.launch{
            charactersUseCase.invoke(10).collect {
                characters.value = it
            }
        }
    }

    fun addSeriesToFavorite(series: Series){
        viewModelScope.launch {
            addSeriesToFavoriteUseCase.invoke(series)
        }
    }

    fun deleteSeriesToFavorite(seriesId: Int){
        viewModelScope.launch {
            deleteSeriesFromFavoriteUseCase.invoke(seriesId)
        }
    }
}