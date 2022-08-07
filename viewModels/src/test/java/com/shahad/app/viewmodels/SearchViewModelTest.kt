package com.shahad.app.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shahad.app.fakerepositories.FakeCharacterRepository
import com.shahad.app.fakerepositories.FakeCreatorRepository
import com.shahad.app.fakerepositories.FakeSeriesRepository
import com.shahad.app.usecases.SearchCharacterUseCase
import com.shahad.app.usecases.SearchCreatorUseCase
import com.shahad.app.usecases.SearchSeriesUseCase
import org.junit.Before
import org.junit.Rule

internal class SearchViewModelTest{

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var characterRepository: FakeCharacterRepository
    lateinit var seriesRepository: FakeSeriesRepository
    lateinit var creatorRepository: FakeCreatorRepository
    lateinit var searchViewModel: SearchViewModel

    lateinit var searchCreatorUseCase: SearchCreatorUseCase
    lateinit var searchCharacterUseCase: SearchCharacterUseCase
    lateinit var searchSeriesUseCase: SearchSeriesUseCase

    @Before
    fun intiViewModel(){
        seriesRepository = FakeSeriesRepository()
        characterRepository= FakeCharacterRepository()
        creatorRepository = FakeCreatorRepository()

        searchSeriesUseCase = SearchSeriesUseCase(seriesRepository)
        searchCreatorUseCase = SearchCreatorUseCase(creatorRepository)
        searchCharacterUseCase = SearchCharacterUseCase(characterRepository)

        searchViewModel = SearchViewModel(searchCharacterUseCase,searchSeriesUseCase,searchCreatorUseCase)

    }





}