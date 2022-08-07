package com.shahad.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shahad.app.core.HomeScreenState
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Creator
import com.shahad.app.core.models.Series
import com.shahad.app.core.showIfSuccess
import com.shahad.app.ui.theme.Spacing
import com.shahad.app.viewmodels.HomeViewModel
import java.util.*

@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel,
){
    Scaffold(
        modifier =  Modifier.fillMaxSize(),
        topBar = { HomeAppBar() },
        content = { padding ->
            val series by viewModel.series.observeAsState()
            val creators by viewModel.creators.observeAsState()
            val characters by viewModel.characters.observeAsState()

            LazyColumn(modifier = Modifier.fillMaxSize()){

                series?.let { state ->
                    item{
                        HandleHomeState(state = state) {
                            SeriesRecycle(series = it, viewModel)
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(MaterialTheme.Spacing.small)) }

                creators?.let {state ->
                    item{
                        HandleHomeState(state = state) {
                            CreatorRecycle(it)
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(MaterialTheme.Spacing.small)) }
                characters?.let {
                    item {
                        TitleSection(title = "Characters") {

                        }
                    }
//                this.CharacterRecycle(character = it)

                    it.showIfSuccess{ state ->
                        state.data?.let { list ->
                            items(list){ character ->
                                CharacterItem(character = character )
                            }
                        }
                    }
                }

            }
        }
    )
}


@Composable
fun <T> HandleHomeState(
    state: HomeScreenState<T?>,
    showResult: @Composable (T) -> Unit
){
    when(state){
        is HomeScreenState.Empty -> ErrorConnectionAnimation()
        HomeScreenState.Loading -> LoadingAnimation()
        is HomeScreenState.Success -> {
            state.data?.let {
                showResult(it)
            }
        }
    }
}

@Composable
fun CreatorRecycle(creators: List<Creator>) {
    Section(
        sectionTitle = "Creators",
        items = creators,
    ) {
        CreatorItem(it)
    }
}

@Composable
fun SeriesRecycle(series: List<Series>, viewModel: HomeViewModel) {
    Section(
        sectionTitle = "Series",
        items = series
    ){
        SeriesItem(
            series = it,
            onClickFavorite = {
                if(it.isFavourite){
                    viewModel.deleteSeriesToFavorite(it.id)
                }else {
                    viewModel.addSeriesToFavorite(it)
                }
            },
            onCLickItem = {
                // nav to details
            }
        )
    }
}

@Composable
fun <T> Section(
    sectionTitle: String,
    items: List<T>,
    itemContent : @Composable (T) -> Unit,
){
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
        TitleSection(title = sectionTitle) {

        }
        LazyRow{
            item {
                Spacer(modifier = Modifier.width(12.dp))
            }
            items(items){
                itemContent(it)
            }
        }
    }
}


@Composable
fun TitleSection(title: String, onClickSeeMore: () -> Unit){
    Row(
        Modifier
            .padding(
                vertical = MaterialTheme.Spacing.small,
            )
            .fillMaxWidth()) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                color = MaterialTheme.colors.onBackground
            ),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.Spacing.medium,)
                .alignByBaseline()
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = "See all ${title.lowercase(Locale.ROOT)}",
            style = TextStyle(
                fontSize = 14.sp,
                color = MaterialTheme.colors.onBackground
            ),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.Spacing.medium,)
                .alignByBaseline()
                .clickable {
                    onClickSeeMore()
                },
            textAlign = TextAlign.End,
        )
    }
}

@Composable
fun HomeAppBar(){
    TopAppBar(
        title = {
            Text(
                text = "Marvel",
                style = TextStyle(
                    fontSize = 28.sp,
                )
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = MaterialTheme.colors.primary,
    )
}