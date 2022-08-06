package com.shahad.app.usecases


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.core.HomeScreenState
import com.shahad.app.usecases.repositories.FakeSeriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class GetSeriesUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeSeriesRepository

    private lateinit var getSeriesUseCase: GetSeriesUseCase

    @Before
    fun intiRepository(){
        repository = FakeSeriesRepository()
        getSeriesUseCase = GetSeriesUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getSeries_ConnectionStable() = runTest{

        //When
        var series = getSeriesUseCase.invoke(10).last()
        //Then
        assertThat(series::class , `is`(HomeScreenState.Success::class))
        assertThat((series as HomeScreenState.Success).data.size, `is`(repository.getSizeOfRemoteSeries()))

        //when
        repository.updateRemoteSeries()
        series = getSeriesUseCase.invoke(10).last()

        //Then
        assertThat(series::class , `is`(HomeScreenState.Success::class))
        assertThat((series as HomeScreenState.Success).data.size, `is`(repository.getSizeOfRemoteSeries()))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getSeries_NoConnectionCacheBefore_ReturnCacheAndUnUpdate() = runTest{
        //When
        var series = getSeriesUseCase.invoke(10).last()
        //Then
        assertThat(series::class , `is`(HomeScreenState.Success::class))
        assertThat((series as HomeScreenState.Success).data.size, `is`(repository.getSizeOfRemoteSeries()))

        //when
        repository.setReturnError(true)
        repository.updateRemoteSeries()

        series = getSeriesUseCase.invoke(10).last()

        //Then
        assertThat(series::class , `is`(HomeScreenState.Success::class))
        assertThat((series as HomeScreenState.Success).data.size, not(repository.getSizeOfRemoteSeries()))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getSeries_NoConnectionNoCache_ReturnEmpty() = runTest{
        //When
        repository.setReturnError(true)
        var Series = getSeriesUseCase.invoke(10).last()
        //Then
        assertThat(Series::class , `is`(HomeScreenState.Empty::class))

        //when
        repository.updateRemoteSeries()

        Series = getSeriesUseCase.invoke(10).last()

        //Then
        assertThat(Series::class , `is`(HomeScreenState.Empty::class))

    }

}