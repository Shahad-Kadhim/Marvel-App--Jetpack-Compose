package com.shahad.app.marvelapp.di

import com.shahad.app.repositories.repositories.*
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideCharacterRepository(
        impl: CharactersRepositoryImp
    ): CharactersRepository

    @Singleton
    @Binds
    abstract fun provideCreatorRepository(
        impl: CreatorsRepositoryImp
    ): CreatorsRepository

    @Singleton
    @Binds
    abstract fun provideSeriesRepository(
        impl: SeriesRepositoryImp
    ): SeriesRepository

    @Singleton
    @Binds
    abstract fun provideStoriesRepository(
        impl: StoriesRepositoryImp
    ): StoriesRepository

}