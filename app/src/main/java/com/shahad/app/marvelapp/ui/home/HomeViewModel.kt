package com.shahad.app.marvelapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahad.app.marvelapp.domain.models.Series
import com.shahad.app.marvelapp.domain.useCases.GetCharactersUseCase
import com.shahad.app.marvelapp.domain.useCases.GetCreatorsUseCase
import com.shahad.app.marvelapp.domain.useCases.GetSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
    private val seriesUseCase: GetSeriesUseCase,
    private val creatorsUseCase: GetCreatorsUseCase,
): ViewModel() {

    val series = MutableLiveData<List<Series>>()

    init {
        viewModelScope.launch {
            seriesUseCase.invoke(10).collect{
                series.value = it
            }
        }
    }

}