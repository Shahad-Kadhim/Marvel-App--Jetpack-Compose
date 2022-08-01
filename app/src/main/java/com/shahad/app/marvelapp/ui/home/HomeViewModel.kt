package com.shahad.app.marvelapp.ui.home

import androidx.lifecycle.ViewModel
import com.shahad.app.marvelapp.domain.useCases.GetCharactersUseCase
import com.shahad.app.marvelapp.domain.useCases.GetCreatorsUseCase
import com.shahad.app.marvelapp.domain.useCases.GetSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val charactersUseCase: GetCharactersUseCase,
    private val seriesUseCase: GetSeriesUseCase,
    private val creatorsUseCase: GetCreatorsUseCase,
): ViewModel() {


}