package com.shahad.app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.shahad.app.core.HomeScreenState
import com.shahad.app.fakerepositories.FakeCharacterRepository
import com.shahad.app.fakerepositories.FakeCreatorRepository
import com.shahad.app.fakerepositories.FakeSeriesRepository
import com.shahad.app.usecases.*
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class HomeViewModelTest{


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var characterRepository : FakeCharacterRepository
    @MockK
    private lateinit var seriesRepository : FakeSeriesRepository
    @MockK
    private lateinit var creatorRepository : FakeCreatorRepository

    @InjectMockKs
    lateinit var getCharactersUseCase: GetCharactersUseCase

    @InjectMockKs
    lateinit var getCreatorsUseCase: GetCreatorsUseCase
    @InjectMockKs
    lateinit var getSeriesUseCase: GetSeriesUseCase
    @InjectMockKs
    lateinit var addSeriesToFavoriteUseCase: AddSeriesToFavoriteUseCase
    @InjectMockKs
    lateinit var deleteSeriesFromFavoriteUseCase: DeleteSeriesFromFavoriteUseCase
    @InjectMockKs
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun intiViewModel(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        homeViewModel.viewModelScope
    }

    @Test
    fun collectCharacter_whenNoConnectionNoCache_EmptyState() = runTest(StandardTestDispatcher()){
        //when
//        withContext(Dispatchers.Main){
//            homeViewModel.collectCharacter()
//        }
//        val characters = getCharactersUseCase.invoke(10)
//        assertThat(characters::class,`is`(HomeScreenState.Success::class))
        coVerify { homeViewModel.collectCharacter() }
    }




}