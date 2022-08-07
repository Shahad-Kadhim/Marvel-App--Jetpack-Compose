package com.shahad.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.shahad.app.core.FavouriteScreenState
import com.shahad.app.core.models.Series
import com.shahad.app.ui.theme.Spacing
import com.shahad.app.viewmodels.FavoriteViewModel

@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel){
    Scaffold(
        modifier =  Modifier.fillMaxSize(),
        content = { paddingValues ->
            val state by viewModel.favoriteSeries.asLiveData().observeAsState()
            Column(Modifier.fillMaxSize()) {
                Row(
                    Modifier
                        .padding(
                            horizontal = MaterialTheme.Spacing.medium,
                            vertical = MaterialTheme.Spacing.medium
                        )
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "go back",
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.Spacing.small)
                            .clickable {
                                navController.navigateUp()
                            },
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                    )
                    Text(
                        text = "Favourite",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_outline_delete_24),
                        contentDescription = "clear all",
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.Spacing.small)
                            .clickable {
                                viewModel.clearFavoriteSeries()
                            },
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                    )

                }

                when(state){
                    FavouriteScreenState.Empty -> NotFoundAnimation()
                    FavouriteScreenState.Loading -> LoadingAnimation()
                    is FavouriteScreenState.Success<*> -> SeriesGridRecycle((state as FavouriteScreenState.Success<List<Series>>).data,viewModel)
                    else -> {}
                }

            }
        }
    )
}

@Composable
fun SeriesGridRecycle(series: List<Series>, viewModel: FavoriteViewModel){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.Spacing.tiny),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.Spacing.tiny),
        modifier = Modifier.padding(horizontal = MaterialTheme.Spacing.medium)
    ) {
        items(series) {
            SeriesItem(
                series =it,
                onCLickItem = {

                },
                onClickFavorite = {
                    viewModel.deleteSeries(it.id)
                }
            )
        }
    }

}