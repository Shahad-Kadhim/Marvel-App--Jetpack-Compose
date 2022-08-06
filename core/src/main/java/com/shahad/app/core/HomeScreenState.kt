package com.shahad.app.core

sealed class HomeScreenState<out T>{
    class Success<T>(val data: T): HomeScreenState<T>()
    object Loading: HomeScreenState<Nothing>()
    object Empty: HomeScreenState<Nothing>()
}
