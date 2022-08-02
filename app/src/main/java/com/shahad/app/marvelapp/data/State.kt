package com.shahad.app.marvelapp.data

sealed class State<out T>{
    object Loading: State<Nothing>()
    class Error(val message: String): State<Nothing>()
    class Success<T>(val data: T?): State<T>()

}
