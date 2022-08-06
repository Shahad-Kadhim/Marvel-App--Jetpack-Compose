package com.shahad.app.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.shahad.app.data.local.MarvelDao
import com.shahad.app.data.local.MarvelDatabase
import com.shahad.app.data.local.entities.CharacterEntity
import com.shahad.app.data.local.entities.CreatorEntity
import com.shahad.app.data.local.entities.SeriesEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class MarvelDaoTest {

    @get:Rule
    val instantExecuteRule = InstantTaskExecutorRule()

    private lateinit var marvelDatabase: MarvelDatabase
    private lateinit var marvelDao: MarvelDao

    @Before
    fun initDatabase(){
        marvelDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MarvelDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        marvelDao = marvelDatabase.marvelDao()
    }

    @After
    fun closeDatabase() = marvelDatabase.close()
    @Test
    fun insertCharacter_firstTime_SavedIt() = runTest{
        //Given
        val character= CharacterEntity(1,"Super Man","","something", "2020/2/2")
        marvelDao.insertCharacters(listOf(character))

        //When
        val characters = marvelDao.getCharacters().asLiveData()

        //Then
        assertThat(characters.getOrAwaitValue(), hasItem(character))
    }

    @Test
    fun insertCharacter_twiceTime_DiscardFirst() = runTest{
        //Given
        val firstCharacter= CharacterEntity(1,"Super Man","","something", "2020/2/2")
        val secondCharacter= CharacterEntity(1,"Super Man","www.superman.jpg","something", "2020/2/6")
        marvelDao.insertCharacters(listOf(firstCharacter,secondCharacter))

        //When
        val characters = marvelDao.getCharacters().asLiveData()

        //Then
        assertThat(characters.getOrAwaitValue(), hasItem(secondCharacter))
        assertThat(characters.getOrAwaitValue(), not(hasItem(firstCharacter)))
    }

    @Test
    fun insertCreator_firstTime_SavedIt() = runTest{
        //Given
        val creator= CreatorEntity(1,"creator name","","2020/2/2")
        marvelDao.insertCreator(listOf(creator))

        //When
        val creators = marvelDao.getCreator().asLiveData()

        //Then
        assertThat(creators.getOrAwaitValue(), hasItem(creator))
    }

    @Test
    fun insertCreator_twiceTime_DiscardFirst() = runTest{
        //Given
        val firstCreator= CreatorEntity(1,"creator name","","2020/2/2")
        val secondCreator= CreatorEntity(1,"creator name","www.creatorImage.com","2020/2/2")
        marvelDao.insertCreator(listOf(firstCreator,secondCreator))

        //When
        val creators = marvelDao.getCreator().asLiveData()

        //Then
        assertThat(creators.getOrAwaitValue(), hasItem(secondCreator))
        assertThat(creators.getOrAwaitValue(), not(hasItem(firstCreator)))
    }

    @Test
    fun insertSeries_firstTime_SavedIt() = runTest{
        //Given
        val series= SeriesEntity(1,"5","title","", "2020/2/2")
        marvelDao.insertSeries(listOf(series))

        //When
        val serieses = marvelDao.getSeries().asLiveData()

        //Then
        assertThat(serieses.getOrAwaitValue(), hasItem(series))
    }

    @Test
    fun insertSeries_twiceTime_DiscardFirst() = runTest{
        //Given
        val firstSeries= SeriesEntity(1,"5","title","", "2020/2/2")
        val secondSeries= SeriesEntity(1,"5","title","www.series.jpg", "2020/2/4")
        marvelDao.insertSeries(listOf(firstSeries,secondSeries))

        //When
        val serieses = marvelDao.getSeries().asLiveData()

        //Then
        assertThat(serieses.getOrAwaitValue(), hasItem(secondSeries))
        assertThat(serieses.getOrAwaitValue(), not(hasItem(firstSeries)))
    }

}