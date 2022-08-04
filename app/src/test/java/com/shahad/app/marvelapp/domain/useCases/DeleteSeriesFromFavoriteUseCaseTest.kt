package com.shahad.app.marvelapp.domain.useCases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.marvelapp.MainCoroutineRule
import com.shahad.app.marvelapp.data.FakeSeriesRepository
import com.shahad.app.marvelapp.data.FavouriteScreenState
import com.shahad.app.marvelapp.domain.models.Series
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class DeleteSeriesFromFavoriteUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeSeriesRepository

    private lateinit var deleteSeriesFromFavoriteUseCase: DeleteSeriesFromFavoriteUseCase

    @Before
    fun intiRepository(){
        repository = FakeSeriesRepository()
        deleteSeriesFromFavoriteUseCase = DeleteSeriesFromFavoriteUseCase(repository)
    }

    @Test
    fun deleteSeriesFromFavorite_deleteIt() = runTest{
        //Given
        val firstSeries = Series(id = 1, title = "s1", imageUrl = "image.jpg")
        val secondSeries = Series(id = 2, title = "s2", imageUrl = "image.jpg")

        //When 
        repository.addFavouriteSeries(firstSeries)
        repository.addFavouriteSeries(secondSeries)
        deleteSeriesFromFavoriteUseCase.invoke(secondSeries.id)
        val favoriteSeries = repository.getFavoriteSeries().last()

        //Then
        assertThat((favoriteSeries as FavouriteScreenState.Success).data, not(hasItem(secondSeries)))
    }

}