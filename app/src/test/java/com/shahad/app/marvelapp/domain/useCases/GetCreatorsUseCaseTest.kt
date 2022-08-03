package com.shahad.app.marvelapp.domain.useCases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.marvelapp.MainCoroutineRule
import com.shahad.app.marvelapp.data.FakeCreatorRepository
import com.shahad.app.marvelapp.data.HomeScreenState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class GetCreatorsUseCaseTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeCreatorRepository

    private lateinit var getCreatorsUseCase: GetCreatorsUseCase

    @Before
    fun intiRepository(){
        repository = FakeCreatorRepository()
        getCreatorsUseCase = GetCreatorsUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCreators_ConnectionStable() = runTest{

        //When
        var creators = getCreatorsUseCase.invoke(10).last()
        //Then
        assertThat(creators::class , `is`(HomeScreenState.Success::class))
        assertThat((creators as HomeScreenState.Success).data.size, `is`(repository.getSizeOfRemoteCreators()))

        //when
        repository.updateRemoteCreators()
        creators = getCreatorsUseCase.invoke(10).last()

        //Then
        assertThat(creators::class , `is`(HomeScreenState.Success::class))
        assertThat((creators as HomeScreenState.Success).data.size, `is`(repository.getSizeOfRemoteCreators()))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCreators_NoConnectionCacheBefore_ReturnCacheAndUnUpdate() = runTest{
        //When
        var creators = getCreatorsUseCase.invoke(10).last()
        //Then
        assertThat(creators::class , `is`(HomeScreenState.Success::class))
        assertThat((creators as HomeScreenState.Success).data.size, `is`(repository.getSizeOfRemoteCreators()))

        //when
        repository.setReturnError(true)
        repository.updateRemoteCreators()

        creators = getCreatorsUseCase.invoke(10).last()

        //Then
        assertThat(creators::class , `is`(HomeScreenState.Success::class))
        assertThat((creators as HomeScreenState.Success).data.size, not(repository.getSizeOfRemoteCreators()))

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCreators_NoConnectionNoCache_ReturnEmpty() = runTest{
        //When
        repository.setReturnError(true)
        var creators = getCreatorsUseCase.invoke(10).last()
        //Then
        assertThat(creators::class , `is`(HomeScreenState.Empty::class))

        //when
        repository.updateRemoteCreators()

        creators = getCreatorsUseCase.invoke(10).last()

        //Then
        assertThat(creators::class , `is`(HomeScreenState.Empty::class))

    }

}