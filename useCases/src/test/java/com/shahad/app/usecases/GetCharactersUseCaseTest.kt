package com.shahad.app.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.core.HomeScreenState
import com.shahad.app.usecases.repositories.FakeCharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class GetCharactersUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeCharacterRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun intiRepository(){
        repository = FakeCharacterRepository()
        getCharactersUseCase = GetCharactersUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCharacters_ConnectionStable() = runTest{

        //When
         var characters = getCharactersUseCase.invoke(10).last()
        //Then
        assertThat(characters::class , `is`(HomeScreenState.Success::class))
        assertThat((characters as HomeScreenState.Success).data.size, `is`(repository.getSizeOfRemoteCharacters()))

        //when
        repository.updateRemoteCharacter()
        characters = getCharactersUseCase.invoke(10).last()

        //Then
        assertThat(characters::class , `is`(HomeScreenState.Success::class))
        assertThat((characters as HomeScreenState.Success).data.size, `is`(repository.getSizeOfRemoteCharacters()))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCharacters_NoConnectionCacheBefore_ReturnCacheAndUnUpdate() = runTest{
        //When
        var characters = getCharactersUseCase.invoke(10).last()
        //Then
        assertThat(characters::class , `is`(HomeScreenState.Success::class))
        assertThat((characters as HomeScreenState.Success).data.size, `is`(repository.getSizeOfRemoteCharacters()))

        //when
        repository.setReturnError(true)
        repository.updateRemoteCharacter()

        characters = getCharactersUseCase.invoke(10).last()

        //Then
        assertThat(characters::class , `is`(HomeScreenState.Success::class))
        assertThat((characters as HomeScreenState.Success).data.size, not(repository.getSizeOfRemoteCharacters()))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCharacters_NoConnectionNoCache_ReturnEmpty() = runTest{
        //When
        repository.setReturnError(true)
        var characters = getCharactersUseCase.invoke(10).last()
        //Then
        assertThat(characters::class , `is`(HomeScreenState.Empty::class))

        //when
        repository.updateRemoteCharacter()

        characters = getCharactersUseCase.invoke(10).last()

        //Then
        assertThat(characters::class , `is`(HomeScreenState.Empty::class))

    }

}