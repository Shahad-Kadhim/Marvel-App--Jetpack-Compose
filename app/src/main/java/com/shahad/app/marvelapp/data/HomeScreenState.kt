package com.shahad.app.marvelapp.data

sealed class HomeScreenState<out T>{
    class Success<T>(val data: T): HomeScreenState<T>()
    object Loading: HomeScreenState<Nothing>()
    object Empty: HomeScreenState<Nothing>()
}
