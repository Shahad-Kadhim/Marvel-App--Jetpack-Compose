package com.shahad.app.core

sealed class DetailsScreenState<out T>{
    object Loading: DetailsScreenState<Nothing>()
    class Error(val message: String): DetailsScreenState<Nothing>()
    class Success<T>(val data: T?): DetailsScreenState<T>()
}
