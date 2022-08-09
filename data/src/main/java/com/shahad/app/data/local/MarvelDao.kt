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
    suspend fun getCharacters(keyword: String = "%", offset: Int =0): List<CharacterEntity>

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

    @Query("SELECT EXISTS (SELECT * FRom SeriesEntity where id = :seriesId and isFavorite = 1)")
    suspend fun ifSeriesFavourite(seriesId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteSeries(series: SeriesEntity)

    @Query("Update SeriesEntity set isFavorite = 0  Where id = :seriesId ")
    suspend fun deleteFavouriteSeries(seriesId: Long)

    @Query("SELECT * From SeriesEntity where isFavorite = 1")
    fun getFavoriteSeries(): Flow<List<SeriesEntity>>

    @Query("Update SeriesEntity set isFavorite = 0 ")
    suspend fun clearFavoriteSeries()


}