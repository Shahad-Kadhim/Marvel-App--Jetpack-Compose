package com.shahad.app.marvelapp.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.shahad.app.marvelapp.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, @DrawableRes val iconId: Int){
    object HomeScreen: Screen(Constants.HOME_ROUTE, R.string.home ,R.drawable.ic_categories)
    object SearchScreen: Screen(Constants.SEARCH_ROUTE,R.string.search, R.drawable.ic_search)
    object FavouriteScreen: Screen(Constants.FAVOURITE_ROUTE,R.string.favouraite, R.drawable.ic_favouraite)

}
