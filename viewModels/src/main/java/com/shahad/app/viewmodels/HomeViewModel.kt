package com.shahad.app.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahad.app.core.HomeScreenState
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Creator
import com.shahad.app.core.models.Series
import com.shahad.app.usecases.*
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

     internal fun collectCreators() {
        viewModelScope.launch {
            creatorsUseCase.invoke(10).collect {
                    creators.value = it
                }
        }
    }

     internal fun collectSeries() {
        viewModelScope.launch{
            seriesUseCase.invoke(10).collect {
                series.value = it
            }
        }
    }

    internal fun collectCharacter(){
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