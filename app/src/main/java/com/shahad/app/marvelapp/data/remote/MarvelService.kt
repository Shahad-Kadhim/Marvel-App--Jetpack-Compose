package com.shahad.app.marvelapp.data.remote

import com.shahad.app.marvelapp.data.remote.response.BaseResponse
import com.shahad.app.marvelapp.data.remote.response.CharacterDto
import com.shahad.app.marvelapp.data.remote.response.CreatorDto
import com.shahad.app.marvelapp.data.remote.response.SeriesDto
import retrofit2.Response
import retrofit2.http.GET
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
    ): Response<BaseResponse<SeriesDto>>


    @GET("creators")
    suspend fun getCreators(
        @Query("limit") numberOfCharacters: Int = 20,
        @Query("nameStartsWith") searchKeyWord: String? = null,
    ): Response<BaseResponse<CreatorDto>>

}