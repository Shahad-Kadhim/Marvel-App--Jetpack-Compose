package com.shahad.app.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.core.SearchScreenState
import com.shahad.app.usecases.repositories.FakeCreatorRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchCreatorUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeCreatorRepository

    private lateinit var searchCreatorUseCase: SearchCreatorUseCase

    @Before
    fun intiRepository(){
        repository = FakeCreatorRepository()
        searchCreatorUseCase = SearchCreatorUseCase(repository)
    }

    @Test
    fun searchCreator_connectionStable_foundIt() = runTest{
        //Given
        val keyWord = "c"
        //When
        val state = searchCreatorUseCase.invoke(keyWord).last()

        //Then
        assertThat(state?.let { it::class }, `is`(SearchScreenState.Success::class))

    }

    @Test
    fun searchCreator_noConnection_returnError() = runTest{
        //Given
        val keyWord = "c"
        //When
        repository.setReturnError(true)
        val state = searchCreatorUseCase.invoke(keyWord).last()

        //Then
        assertThat(state?.let { it::class }, `is`(SearchScreenState.Error::class))

    }

    @Test
    fun searchCreator_noResult_returnEmpty() = runTest{
        //Given
        val keyWord = "c22"
        //When
        val state = searchCreatorUseCase.invoke(keyWord).last()

        //Then
        assertThat(state?.let { it::class }, `is`(SearchScreenState.Empty::class))

    }

}