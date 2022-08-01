package com.shahad.app.marvelapp.util

import com.shahad.app.marvelapp.data.remote.response.Thumbnail
import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String {
    with(MessageDigest.getInstance("MD5")){
        return BigInteger(1, digest(toByteArray())).toString(16).padStart(32, '0')
    }
}

fun Thumbnail?.toImageUrl()=
    "${this?.path}.${this?.extension}".replaceHttpWithHttps()


fun String.replaceHttpWithHttps() =
    this.replace("http","https")
