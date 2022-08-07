package com.shahad.app.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.core.FavouriteScreenState
import com.shahad.app.core.models.Series
import com.shahad.app.repositories.setIsFavourite
import com.shahad.app.fakerepositories.FakeSeriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class GetFavoriteSeriesUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeSeriesRepository

    private lateinit var getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase

    @Before
    fun intiRepository(){
        repository = FakeSeriesRepository()
        getFavoriteSeriesUseCase = GetFavoriteSeriesUseCase(repository)
    }


    @Test
    fun getFavouriteSeries_ReturnSeriesList() = runTest{
        //Given
        val series = Series(id = 1, title = "s1", imageUrl = "image.jpg")
        repository.addFavouriteSeries(series)

        //When
        val favoriteSeries = getFavoriteSeriesUseCase.invoke().last()

        //Then
        assertThat(favoriteSeries::class, `is`(FavouriteScreenState.Success::class))
        assertThat((favoriteSeries as FavouriteScreenState.Success).data, hasItem(series.setIsFavourite(true)))

    }

    @Test
    fun getFavouriteSeries_NoFavorite_ReturnEmpty() = runTest{

        //When
        val favoriteSeries = getFavoriteSeriesUseCase.invoke().last()

        //Then
        assertThat(favoriteSeries::class, `is`(FavouriteScreenState.Empty::class))
    }

    @Test
    fun getFavouriteSeries_AfterDeleteSeries_SeriesNotFound() = runTest{
        //Given
        val firstSeries =Series(id = 1, title = "s1", imageUrl = "image.jpg")
        val secondSeries =Series(id = 2, title = "s2", imageUrl = "image2.jpg")
        repository.addFavouriteSeries(firstSeries)
        repository.addFavouriteSeries(secondSeries)

        //When
        repository.deleteFavouriteSeries(secondSeries.id)
        val favoriteSeries = getFavoriteSeriesUseCase.invoke().last()

        //Then
        assertThat((favoriteSeries as FavouriteScreenState.Success).data, not(hasItem(secondSeries)))
    }

    @Test
    fun getFavouriteSeries_AfterDeleteLastItem_returnEmpty() = runTest{
        //Given
        val series =Series(id = 1, title = "s1", imageUrl = "image.jpg")
        repository.addFavouriteSeries(series)

        //When
        repository.deleteFavouriteSeries(series.id)
        val favoriteSeries = getFavoriteSeriesUseCase.invoke().last()

        //Then
        assertThat(favoriteSeries::class, `is`(FavouriteScreenState.Empty::class))
    }


    @Test
    fun getFavouriteSeries_NoFavoriteSeries_returnEmpty() = runTest{
        //When
        val favoriteSeries = getFavoriteSeriesUseCase.invoke().last()
        //Then
        assertThat(favoriteSeries::class, `is`(FavouriteScreenState.Empty::class))
    }



}