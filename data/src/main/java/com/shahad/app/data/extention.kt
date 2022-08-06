package com.shahad.app.data

import com.shahad.app.data.remote.response.Thumbnail
import java.math.BigInteger
import java.security.MessageDigest

fun Thumbnail?.toImageUrl()=
    "${this?.path}.${this?.extension}".replaceHttpWithHttps()


fun String.replaceHttpWithHttps() =
    this.replace("http","https")


fun String.md5(): String {
    with(MessageDigest.getInstance("MD5")){
        return BigInteger(1, digest(toByteArray())).toString(16).padStart(32, '0')
    }
}