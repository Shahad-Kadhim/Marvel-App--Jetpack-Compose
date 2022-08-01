package com.shahad.app.marvelapp.data.local

import androidx.room.*
import com.shahad.app.marvelapp.data.local.entities.CharacterEntity
import com.shahad.app.marvelapp.data.local.entities.CreatorEntity
import com.shahad.app.marvelapp.data.local.entities.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * From CharacterEntity")
    fun getCharacters(): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeries(series: List<SeriesEntity>)

    @Query("SELECT * From SeriesEntity")
    fun getSeries(): Flow<List<SeriesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCreator(series: List<CreatorEntity>)

    @Query("SELECT * From CreatorEntity")
    fun getCreator(): Flow<List<CreatorEntity>>
}