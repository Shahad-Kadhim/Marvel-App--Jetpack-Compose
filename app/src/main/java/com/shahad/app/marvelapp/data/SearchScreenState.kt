package com.shahad.app.marvelapp.data

sealed class SearchScreenState<out T>{
    object Loading: SearchScreenState<Nothing>()
    class Error(val message: String): SearchScreenState<Nothing>()
    class Success<T>(val data: T?): SearchScreenState<T>()
    object Empty: SearchScreenState<Nothing>()
}
