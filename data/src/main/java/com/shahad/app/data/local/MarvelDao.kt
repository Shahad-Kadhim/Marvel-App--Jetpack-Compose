package com.shahad.app.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.shahad.app.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * From CharacterEntity WHERE name LIKE :keyword limit 10 offset :offset")
    suspend fun getCharacters(keyword: String = "", offset: Int =0): List<CharacterEntity>

    @Query("SELECT * From CharacterEntity where name LIKE :keyword ")
    fun pagingSource(keyword: String): PagingSource<Int, CharacterEntity>

    @Query("DELETE From CharacterEntity")
    suspend fun clearAllCharacter()

    @Query("SELECT COUNT() FROM CharacterEntity")
    suspend fun getSizeOfCharacter(): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<SeriesEntity>)

    @Query("SELECT * From SeriesEntity")
    fun getSeries(): Flow<List<SeriesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreator(series: List<CreatorEntity>)

    @Query("SELECT * From CreatorEntity")
    fun getCreator(): Flow<List<CreatorEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteSeries(series: FavoriteEntity)

    @Query("DELETE From FavoriteEntity Where  id = :seriesId")
    suspend fun deleteFavouriteSeries(seriesId: Int)

    @Query("SELECT * From FavoriteEntity")
    fun getFavoriteSeries(): Flow<List<FavoriteEntity>>

    @Query("DELETE From FavoriteEntity")
    suspend fun clearFavoriteSeries()


}