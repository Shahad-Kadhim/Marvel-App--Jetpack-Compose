package com.shahad.app.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.core.FavouriteScreenState
import com.shahad.app.core.models.Series
import com.shahad.app.repositories.setIsFavourite
import com.shahad.app.fakerepositories.FakeSeriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class AddSeriesToFavoriteUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeSeriesRepository

    private lateinit var addSeriesToFavoriteUseCase: AddSeriesToFavoriteUseCase

    @Before
    fun intiRepository(){
        repository = FakeSeriesRepository()
        addSeriesToFavoriteUseCase = AddSeriesToFavoriteUseCase(repository)
    }

    @Test
    fun addSeriesToFavorite_saveIt() = runTest{
        //Given
        val series = Series(id = 1, title = "s1", imageUrl = "image.jpg")

        //When
        addSeriesToFavoriteUseCase.invoke(series)
        val favoriteSeries = repository.getFavoriteSeries().last()

        //Then
        assertThat(favoriteSeries::class,`is`(FavouriteScreenState.Success::class))
        assertThat((favoriteSeries as FavouriteScreenState.Success).data, hasItem(series.setIsFavourite(true)))

    }

    @Test
    fun addSeriesToFavorite_twice_SaveItOnce() = runTest{
        //Given
        val series = Series(id = 1, title = "s1", imageUrl = "image.jpg")

        //When
        addSeriesToFavoriteUseCase.invoke(series)
        addSeriesToFavoriteUseCase.invoke(series)
        val favoriteSeries = repository.getFavoriteSeries().last()

        //Then
        assertThat(favoriteSeries::class,`is`(FavouriteScreenState.Success::class))
        assertThat((favoriteSeries as FavouriteScreenState.Success).data.size, `is`(1))

    }

}