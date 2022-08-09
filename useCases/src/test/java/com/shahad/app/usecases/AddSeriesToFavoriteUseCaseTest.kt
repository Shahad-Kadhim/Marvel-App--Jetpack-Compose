package com.shahad.app.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.core.models.Series
import com.shahad.app.usecases.fakeRepositories.FakeSeriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
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
        val series = Series(id = 1, title = "s1", imageUrl = "image.jpg", isFavourite = true)

        //When
        addSeriesToFavoriteUseCase.invoke(series)
        val favoriteSeries = repository.getFavoriteSeries().last()

        //Then
        assertThat(favoriteSeries, hasItem(series))

    }

    @Test
    fun addSeriesToFavorite_twice_SaveItOnce() = runTest{
        //Given
        val series = Series(id = 1, title = "s1", imageUrl = "image.jpg", isFavourite = true)

        //When
        addSeriesToFavoriteUseCase.invoke(series)
        addSeriesToFavoriteUseCase.invoke(series)
        val favoriteSeries = repository.getFavoriteSeries().last()
        println(favoriteSeries)
        assertThat(favoriteSeries.size, `is`(1))

    }

}