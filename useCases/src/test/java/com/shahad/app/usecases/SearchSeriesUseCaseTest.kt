package com.shahad.app.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.core.SearchScreenState
import com.shahad.app.usecases.repositories.FakeSeriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchSeriesUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeSeriesRepository

    private lateinit var searchSeriesUseCase: SearchSeriesUseCase

    @Before
    fun intiRepository(){
        repository = FakeSeriesRepository()
        searchSeriesUseCase = SearchSeriesUseCase(repository)
    }

    @Test
    fun searchSeries_connectionStable_foundIt() = runTest{
        //Given
        val keyWord = "s"
        //When
        val state = searchSeriesUseCase.invoke(keyWord).last()

        //Then
        MatcherAssert.assertThat(state?.let { it::class }, CoreMatchers.`is`(SearchScreenState.Success::class))

    }

    @Test
    fun searchSeries_noConnection_returnError() = runTest{
        //Given
        val keyWord = "c"
        //When
        repository.setReturnError(true)
        val state = searchSeriesUseCase.invoke(keyWord).last()

        //Then
        MatcherAssert.assertThat(state?.let { it::class }, CoreMatchers.`is`(SearchScreenState.Error::class))

    }

    @Test
    fun searchSeries_noResult_returnEmpty() = runTest{
        //Given
        val keyWord = "c22"
        //When
        val state = searchSeriesUseCase.invoke(keyWord).last()

        //Then
        MatcherAssert.assertThat(state?.let { it::class }, CoreMatchers.`is`(SearchScreenState.Empty::class))

    }

}