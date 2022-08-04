package com.shahad.app.marvelapp.data

sealed class FavouriteScreenState<out T>{
    object Loading: FavouriteScreenState<Nothing>()
    class Success<T>(val data: T?): FavouriteScreenState<T>()
    object Empty: FavouriteScreenState<Nothing>()
}
