package com.shahad.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shahad.app.data.local.entities.CharacterEntity
import com.shahad.app.data.local.entities.CreatorEntity
import com.shahad.app.data.local.entities.FavoriteEntity
import com.shahad.app.data.local.entities.SeriesEntity

@TypeConverters(Convertor::class)
@Database(entities = [CharacterEntity::class, SeriesEntity::class, CreatorEntity::class, FavoriteEntity::class] , version = 1)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun marvelDao(): MarvelDao

    companion object{
        const val DATABASE_NAME = "MARVEL_DATABASE"
    }

}