package com.shahad.app.marvelapp.ui.search

import android.util.Log
import androidx.annotation.RawRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.shahad.app.marvelapp.R
import com.shahad.app.marvelapp.data.State
import com.shahad.app.marvelapp.domain.models.Character
import com.shahad.app.marvelapp.domain.models.Creator
import com.shahad.app.marvelapp.domain.models.Series
import com.shahad.app.marvelapp.ui.home.*
import com.shahad.app.marvelapp.ui.theme.Spacing
import com.shahad.app.marvelapp.util.FilterType
import kotlinx.coroutines.launch

@Composable
fun Search(navController: NavController, viewModel: SearchViewModel){
    Scaffold(
        modifier =  Modifier.fillMaxSize(),
    ) {
        val search by viewModel.search.collectAsState()
        val filterType by viewModel.filterType.collectAsState()
        val isFiltersVisible by viewModel.isFiltersVisible.collectAsState()
        val filters = listOf(FilterType.CHARACTER, FilterType.CREATOR, FilterType.SERIES)
        val rotateAnimation = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()
        val characters by viewModel.characters.observeAsState()
        val creators by viewModel.creator.observeAsState()
        val series by viewModel.series.observeAsState()
        Column(Modifier.fillMaxSize()) {
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
                    )
                    BasicTextField(
                        value = search,
                        onValueChange = {
                            viewModel.search.value = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1
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
                    ShowCharacters(characters)
                }
                FilterType.CREATOR -> {
//                            ShowCreators(creators)
                }
                FilterType.SERIES -> {
//                            ShowSeries(series)
                }
            }
        }

    }
}

@Composable
fun LazyListScope.ShowSeries(state: State<List<Series>?>?) {
    HandleState(state = state) {
        this.items(it){

        }
    }
}

@Composable
fun <T> HandleState(
    state: State<T?>?,
    showResult: @Composable (T) -> Unit
){
    when(state){
        is State.Error -> ErrorConnectionAnimation()
        State.Loading -> LoadingAnimation()
        is State.Success -> {
            state.data?.let {
                showResult(it)
            }
        }
    }
}

@Composable
fun BasicLottie(
    @RawRes lottieId: Int,
    modifier: Modifier =Modifier
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieId))
        LottieAnimation(
            composition = composition,
            isPlaying = true,
            iterations = LottieConstants.IterateForever,
            modifier = modifier
                .width(172.dp)
                .height(172.dp),
        )
    }
}
@Composable
fun LoadingAnimation(){
    BasicLottie(lottieId = R.raw.loading_lottie)
}

@Composable
fun ErrorConnectionAnimation(){
    BasicLottie(lottieId = R.raw.error)
}
@Composable
fun LazyListScope.ShowCreators(state: State<List<Creator>?>?) {
    HandleState(state = state) {
        this.items(it){

        }
    }
}

@Composable
fun ShowCharacters(state: State<List<Character>?>?) {
    HandleState(state = state) {
        Log.i("ZZZ",it.toString())
        LazyColumn{
            items(it) { character ->
                CharacterItem(character = character)
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
        color = if (isSelected) MaterialTheme.colors.primary else Color.LightGray,
        contentColor = MaterialTheme.colors.onPrimary
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