package com.shahad.app.data.remote

import com.shahad.app.data.remote.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService{

    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") numberOfCharacters: Int = 20,
        @Query("nameStartsWith") searchKeyWord: String? = null,
    ): Response<BaseResponse<CharacterDto>>


    @GET("series")
    suspend fun getSeries(
        @Query("limit") numberOfCharacters: Int = 20,
        @Query("titleStartsWith") searchKeyWord: String? = null,
        ): Response<BaseResponse<SeriesDto>>


    @GET("creators")
    suspend fun getCreators(
        @Query("limit") numberOfCharacters: Int = 20,
        @Query("nameStartsWith") searchKeyWord: String? = null,
    ): Response<BaseResponse<CreatorDto>>


    @GET("characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: Long
    ): Response<BaseResponse<CharacterDto>>


    @GET("characters/{characterId}/stories")
    suspend fun getCharacterStoriesById(
        @Path("characterId") characterId: Long
    ): Response<BaseResponse<StoryDto>>



}