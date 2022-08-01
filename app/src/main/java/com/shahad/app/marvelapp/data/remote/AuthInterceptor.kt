package com.shahad.app.marvelapp.data.remote

import com.shahad.app.marvelapp.BuildConfig
import com.shahad.app.marvelapp.util.md5
import okhttp3.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor() :Interceptor {

    private val timeStamp = System.currentTimeMillis().toString()
    private val apikey =BuildConfig.PUPLIC_API_KEY
    private val hash ="$timeStamp${BuildConfig.PRIVATE_API_KEY}$apikey".md5()

    override fun intercept(chain: Interceptor.Chain): Response {
        with(chain.request()){
            url.newBuilder()
                .addQueryParameters()
                .build().also { httpUrl ->
                    return chain.proceed(this.newBuilder().url(httpUrl).build())
                }
        }
    }

    private fun HttpUrl.Builder.addQueryParameters(): HttpUrl.Builder {
        return this.apply {
            addQueryParameter(API_KEY_PARAMETER , apikey)
            addQueryParameter(TIMESTAMP_PARAMETER , timeStamp)
            addQueryParameter(HASH_PARAMETER , hash)
        }
    }

    companion object{
        private const val API_KEY_PARAMETER ="apikey"
        private const val TIMESTAMP_PARAMETER ="ts"
        private const val HASH_PARAMETER ="hash"
    }
}