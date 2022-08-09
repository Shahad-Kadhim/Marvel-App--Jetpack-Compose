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
        val characters = marvelDao.getCharacters()

        //Then
        assertThat(characters, hasItem(character))
    }

    @Test
    fun insertCharacter_twiceTime_DiscardFirst() = runTest{
        //Given
        val firstCharacter= CharacterEntity(1,"Super Man","","something", "2020/2/2")
        val secondCharacter= CharacterEntity(1,"Super Man","www.superman.jpg","something", "2020/2/6")
        marvelDao.insertCharacters(listOf(firstCharacter,secondCharacter))

        //When
        val characters = marvelDao.getCharacters()

        //Then
        assertThat(characters, hasItem(secondCharacter))
        assertThat(characters, not(hasItem(firstCharacter)))
    }

    @Test
    fun insertSeries_firstTime_SavedIt() = runTest{
        //Given
        val series= SeriesEntity(1,"5","title","", "2020/2/2",false)
        marvelDao.insertSeries(listOf(series))

        //When
        val serieses = marvelDao.getSeries().asLiveData()

        //Then
        assertThat(serieses.getOrAwaitValue(), hasItem(series))
    }

    @Test
    fun insertSeries_twiceTime_DiscardFirst() = runTest{
        //Given
        val firstSeries= SeriesEntity(1,"5","title","", "2020/2/2",false)
        val secondSeries= SeriesEntity(1,"5","title","www.series.jpg", "2020/2/4",false)
        marvelDao.insertSeries(listOf(firstSeries,secondSeries))

        //When
        val serieses = marvelDao.getSeries().asLiveData()

        //Then
        assertThat(serieses.getOrAwaitValue(), hasItem(secondSeries))
        assertThat(serieses.getOrAwaitValue(), not(hasItem(firstSeries)))
    }

    @Test
    fun clearCharacters()= runTest{
        //Given
        val firstCharacter= CharacterEntity(1,"Super Man","","something", "2020/2/2")
        val secondCharacter= CharacterEntity(2,"Spider Man","www.spiderMan.jpg","something", "2020/2/6")
        marvelDao.insertCharacters(listOf(firstCharacter,secondCharacter))

        //When
        marvelDao.clearAllCharacter()
        val characters = marvelDao.getCharacters()

        //Then
        assertThat(characters.isEmpty(), `is`(true))
    }

    @Test
    fun getCharactersSize_IsCorrect()= runTest{
        //Given
        val firstCharacter= CharacterEntity(1,"Super Man","","something", "2020/2/2")
        val secondCharacter= CharacterEntity(2,"Spider Man","www.spiderMan.jpg","something", "2020/2/6")
        marvelDao.insertCharacters(listOf(firstCharacter,secondCharacter))

        //When
        val characters = marvelDao.getCharacters()

        //Then
        assertThat(characters.size, `is`(2))
    }

    @Test
    fun getSeriesISFavourite_IfItExistsAndFavourite_ReturnTrue()= runTest{
        //Given
        val series= SeriesEntity(1,"5","title","", "2020/2/2",true)
        marvelDao.insertSeries(listOf(series))

        //When
        val isFavourite = marvelDao.ifSeriesFavourite(series.id)

        //Then
        assertThat(isFavourite, `is`(series.isFavorite))

    }

    @Test
    fun getSeriesISFavourite_IfItNotExists_ReturnFalse()= runTest{
        //Given
        val series= SeriesEntity(1,"5","title","", "2020/2/2",true)

        //When
        val isFavourite = marvelDao.ifSeriesFavourite(series.id)

        //Then
        assertThat(isFavourite, `is`(false))

    }

    @Test
    fun getSeriesISFavourite_IfItExistsAndNotFavourite_ReturnFalse()= runTest{
        //Given
        val series= SeriesEntity(1,"5","title","", "2020/2/2",false)

        //When
        val isFavourite = marvelDao.ifSeriesFavourite(series.id)

        //Then
        assertThat(isFavourite, `is`(series.isFavorite))

    }


    @Test
    fun insertFavouriteSeries_IsReadyExists_SavedLastOne()= runTest{
        //Given
        val unFavouriteSeries= SeriesEntity(1,"5","title","", "2020/2/2",false)
        val favouriteSeries= SeriesEntity(1,"5","title","", "2020/2/2",true)
        marvelDao.insertSeries(listOf(unFavouriteSeries,favouriteSeries))
        //When
        val serieses = marvelDao.getSeries().asLiveData()

        //Then
        assertThat(serieses.getOrAwaitValue(), hasItem(favouriteSeries))
        assertThat(serieses.getOrAwaitValue(), not(hasItem(unFavouriteSeries)))

    }

    @Test
    fun insertFavouriteSeries_IsNotExists_SavedIt()= runTest{
        //Given
        val series= SeriesEntity(1,"5","title","", "2020/2/2",true)
        marvelDao.insertSeries(listOf(series))
        //When
        val serieses = marvelDao.getSeries().asLiveData()

        //Then
        assertThat(serieses.getOrAwaitValue(), hasItem(series))
    }


    @Test
    fun deleteFavouriteSeries_MakeItUnFavourite()= runTest{
        //Given
        val series= SeriesEntity(1,"5","title","", "2020/2/2",true)
        marvelDao.insertSeries(listOf(series))
        marvelDao.deleteFavouriteSeries(series.id)

        //When
        val serieses = marvelDao.getSeries().asLiveData()

        //Then
        assertThat(serieses.getOrAwaitValue(), hasItem(series.apply { isFavorite =false }))
        assertThat(serieses.getOrAwaitValue().find { it.id == series.id }?.isFavorite,  `is`(false))
    }


    @Test
    fun deleteFavouriteSeries_ISNotExists_MakeItUnFavourite()= runTest{
        //Given
        val series= SeriesEntity(1,"5","title","", "2020/2/2",true)
        marvelDao.deleteFavouriteSeries(series.id)

        //When
        val serieses = marvelDao.getSeries().asLiveData()

        //Then
        assertThat(serieses.getOrAwaitValue(), not(hasItem(series)))
    }


    @Test
    fun clearFavouriteSeries()= runTest{
        //Given
        //Given
        val firstSeries= SeriesEntity(1,"5","title1","", "2020/2/2",false)
        val secondSeries= SeriesEntity(2,"3.5","title2","www.series.jpg", "2020/2/4",true)
        val thirdSeries= SeriesEntity(3,"3.5","title3","www.series.jpg", "2020/2/4",true)
        marvelDao.insertSeries(listOf(firstSeries,secondSeries,thirdSeries))

        //When
        marvelDao.clearFavoriteSeries()
        val serieses = marvelDao.getSeries().asLiveData()
        val favouriteSeries = marvelDao.getFavoriteSeries().asLiveData()
        //Then
        assertThat(serieses.getOrAwaitValue().filter { it.isFavorite }.size, `is`(0))
        assertThat(favouriteSeries.getOrAwaitValue(), `is`(emptyList<SeriesEntity>()))
    }

}