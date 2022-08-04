package com.shahad.app.marvelapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahad.app.marvelapp.domain.useCases.DeleteSeriesFromFavoriteUseCase
import com.shahad.app.marvelapp.domain.useCases.GetFavoriteSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase,
    private val deleteSeriesFromFavoriteUseCase: DeleteSeriesFromFavoriteUseCase
): ViewModel() {

    val favoriteSeries = getFavoriteSeriesUseCase.invoke()

    fun deleteSeries(seriesId: Int){
        viewModelScope.launch {
            deleteSeriesFromFavoriteUseCase.invoke(seriesId)
        }
    }

    fun clearFavoriteSeries() {
        viewModelScope.launch {
            deleteSeriesFromFavoriteUseCase.clearFavouriteSeries()
        }
    }
}