package com.shahad.app.data.local

import androidx.room.*
import com.shahad.app.data.local.entities.CharacterEntity
import com.shahad.app.data.local.entities.CreatorEntity
import com.shahad.app.data.local.entities.FavoriteEntity
import com.shahad.app.data.local.entities.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * From CharacterEntity")
    fun getCharacters(): Flow<List<CharacterEntity>>

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