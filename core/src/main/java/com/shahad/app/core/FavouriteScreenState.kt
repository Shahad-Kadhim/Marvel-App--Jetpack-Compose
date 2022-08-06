package com.shahad.app.core

sealed class FavouriteScreenState<out T>{
    object Loading: FavouriteScreenState<Nothing>()
    class Success<T>(val data: T): FavouriteScreenState<T>()
    object Empty: FavouriteScreenState<Nothing>()
}
