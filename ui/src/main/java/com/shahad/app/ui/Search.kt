package com.shahad.app.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shahad.app.core.Constants
import com.shahad.app.core.FilterType
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Creator
import com.shahad.app.core.models.Series
import com.shahad.app.ui.theme.Spacing
import com.shahad.app.viewmodels.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun Search(navController: NavController, viewModel: SearchViewModel){
    Scaffold(
        modifier =  Modifier.fillMaxSize(),
        content = { padding ->
            val search by viewModel.search.collectAsState()
            val filterType by viewModel.filterType.collectAsState()
            val isFiltersVisible by viewModel.isFiltersVisible.collectAsState()
            val filters = listOf(FilterType.CHARACTER, FilterType.CREATOR, FilterType.SERIES)
            val rotateAnimation = remember { Animatable(0f) }
            val scope = rememberCoroutineScope()
            val characters = viewModel.characters.collectAsLazyPagingItems()
            val creators = viewModel.creator.collectAsLazyPagingItems()
            val series = viewModel.series.collectAsLazyPagingItems()
            Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize()) {
                Row(
                    Modifier
                        .padding(
                            horizontal = MaterialTheme.Spacing.medium,
                            vertical = MaterialTheme.Spacing.medium
                        )
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.Center
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
                    Row(
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.Spacing.small)
                            .clip(RoundedCornerShape(MaterialTheme.Spacing.small))
                            .weight(1f)
                            .fillMaxWidth()
                            .height(42.dp)
                            .background(MaterialTheme.colors.secondary),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "search",
                            modifier = Modifier.padding(
                                vertical = MaterialTheme.Spacing.tiny,
                                horizontal = MaterialTheme.Spacing.medium
                            ),
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                        )
                        BasicTextField(
                            value = search,
                            onValueChange = {
                                viewModel.search.value = it
                            },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            textStyle = TextStyle(
                                color = MaterialTheme.colors.onBackground
                            ),
                            singleLine = true
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_drop_list),
                        contentDescription = "filter",
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.Spacing.small)
                            .toggleable(
                                value = isFiltersVisible,
                                onValueChange = {
                                    viewModel.isFiltersVisible.value = !isFiltersVisible
                                    scope.launch {
                                        rotateAnimation.animateTo(
                                            targetValue = if (rotateAnimation.value == 180f) 0f else 180f,
                                            animationSpec = tween(500, easing = LinearEasing)
                                        )
                                    }
                                }
                            )
                            .rotate(rotateAnimation.value),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                    )
                }
                takeIf { isFiltersVisible }?.let {
                    ChipGroup(
                        items = filters,
                        selectedItem = filterType,
                    ) { selectedType ->
                        viewModel.filterType.tryEmit(selectedType)
                    }
                }
                when (filterType) {
                    FilterType.CHARACTER -> {
                        ShowCharacters(characters,navController)
                    }
                    FilterType.CREATOR -> {
                        ShowCreators(creators)
                    }
                    FilterType.SERIES -> {
                        ShowSeries(series){ series ->
                            if(series.isFavourite){
                                viewModel.deleteSeriesToFavorite(series.id)
                            }else {
                                viewModel.addSeriesToFavorite(series)
                            }
                        }
                    }
                }
            }

        }
    )
}

@Composable
fun ShowSeries(series: LazyPagingItems<Series>, onClickFavourite: (Series) -> Unit ) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.Spacing.tiny),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.Spacing.tiny),
        modifier = Modifier.padding(horizontal = MaterialTheme.Spacing.medium, vertical = MaterialTheme.Spacing.small)
    ) {
        items(series.itemCount) { index ->
            series[index]?.let {
                SeriesItem(series = it) {
                    onClickFavourite(it)
                }
            }
        }
    }
}

@Composable
fun ShowCreators(creators: LazyPagingItems<Creator>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.Spacing.tiny),
        modifier = Modifier.padding(horizontal = MaterialTheme.Spacing.medium, vertical = MaterialTheme.Spacing.small)
    ) {
        items(creators.itemCount) { index ->
            creators[index]?.let { creator ->
                CreatorItem(creator)
            }
        }
    }
}

@Composable
fun ShowCharacters(state: LazyPagingItems<Character>, navController: NavController) {
    LazyColumn{
        items(state.itemCount) { index ->
            state[index]?.let {
                CharacterItem(character = it){
                    navController.navigate("${Constants.DETAILS_SCREEN}/${it.id}")
                }
            }
        }
    }

}



@Composable
fun<T> Chip(
    item: T,
    name: String,
    isSelected: Boolean = false ,
    onSelected: (T) -> Unit ,
){
    Surface(
        modifier = Modifier
            .padding(MaterialTheme.Spacing.tiny)
            .wrapContentSize(),
        shape = RoundedCornerShape(MaterialTheme.Spacing.small),
        color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        contentColor = if (isSelected) MaterialTheme.colors.onPrimary else  MaterialTheme.colors.onSecondary
    ){
        Row(
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onSelected(item)
                    }
                )
        ) {
            Text(
                text =name,
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(MaterialTheme.Spacing.small)
            )
        }
    }
}

@Composable
fun ChipGroup(
    items: List<FilterType>,
    selectedItem: FilterType?,
    onSelected: (FilterType) -> Unit
){
    LazyRow(
        modifier = Modifier.padding(16.dp,0.dp),
    ){
        items(items){ type ->
            Chip(
                name = type.name,
                item= type,
                onSelected = onSelected,
                isSelected = selectedItem == type
            )
        }
    }

}