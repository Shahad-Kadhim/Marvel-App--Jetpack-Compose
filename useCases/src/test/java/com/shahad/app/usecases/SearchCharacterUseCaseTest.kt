package com.shahad.app.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.core.SearchScreenState
import com.shahad.app.usecases.repositories.FakeCharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SearchCharacterUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeCharacterRepository

    private lateinit var searchCharacterUseCase: SearchCharacterUseCase

    @Before
    fun intiRepository(){
        repository = FakeCharacterRepository()
        searchCharacterUseCase = SearchCharacterUseCase(repository)
    }

    @Test
    fun searchCharacter_connectionStable_foundIt() = runTest{
        //Given
        val keyWord = "c"
        //When
        val state = searchCharacterUseCase.invoke(keyWord).last()

        //Then
        assertThat(state?.let { it::class },`is`(SearchScreenState.Success::class))

    }

    @Test
    fun searchCharacter_noConnection_returnError() = runTest{
        //Given
        val keyWord = "c"
        //When
        repository.setReturnError(true)
        val state = searchCharacterUseCase.invoke(keyWord).last()

        //Then
        assertThat(state?.let { it::class },`is`(SearchScreenState.Error::class))

    }

    @Test
    fun searchCharacter_noResult_returnEmpty() = runTest{
        //Given
        val keyWord = "c22"
        //When
        val state = searchCharacterUseCase.invoke(keyWord).last()

        //Then
        assertThat(state?.let { it::class },`is`(SearchScreenState.Empty::class))

    }

}