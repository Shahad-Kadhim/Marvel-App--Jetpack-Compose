package com.shahad.app.marvelapp.data

sealed class State<out T>{
    object Loading: State<Nothing>()
    class Error(message: String): State<Nothing>()
    class Success<T>(data: T?): State<T>()

}
