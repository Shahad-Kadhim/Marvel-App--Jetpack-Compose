package com.shahad.app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.fakerepositories.FakeSeriesRepository
import com.shahad.app.usecases.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class FavoriteViewModelTest{

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var seriesRepository: FakeSeriesRepository
    lateinit var favoriteViewModel: FavoriteViewModel

    lateinit var deleteSeriesFromFavoriteUseCase: DeleteSeriesFromFavoriteUseCase
    lateinit var getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase

    @Before
    fun intiViewModel(){
        seriesRepository = FakeSeriesRepository()
        deleteSeriesFromFavoriteUseCase = DeleteSeriesFromFavoriteUseCase(seriesRepository)
        getFavoriteSeriesUseCase = GetFavoriteSeriesUseCase(seriesRepository)

        favoriteViewModel = FavoriteViewModel(getFavoriteSeriesUseCase,deleteSeriesFromFavoriteUseCase)
    }



}