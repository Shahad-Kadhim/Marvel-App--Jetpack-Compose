package com.shahad.app.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.core.FavouriteScreenState
import com.shahad.app.core.models.Series
import com.shahad.app.fakerepositories.FakeSeriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
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


    @Test
    fun clearFavoriteSeries_returnEmpty() = runTest{
        //Given
        val firstSeries = Series(id = 1, title = "s1", imageUrl = "image.jpg")
        val secondSeries = Series(id = 2, title = "s2", imageUrl = "image.jpg")

        //When
        repository.addFavouriteSeries(firstSeries)
        repository.addFavouriteSeries(secondSeries)
        deleteSeriesFromFavoriteUseCase.clearFavouriteSeries()
        val favoriteSeries = repository.getFavoriteSeries().last()

        //Then
        assertThat(favoriteSeries::class, `is`(FavouriteScreenState.Empty::class))
    }


}