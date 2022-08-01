package com.shahad.app.marvelapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shahad.app.marvelapp.domain.models.Series
import com.shahad.app.marvelapp.ui.theme.Colors
import com.shahad.app.marvelapp.ui.theme.Spacing
import java.util.*

@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel,
){
    Scaffold(
        modifier =  Modifier.fillMaxSize(),
        topBar = { HomeAppBar() }
    ) {
        val series by viewModel.series.observeAsState()
        LazyColumn(modifier = Modifier.fillMaxSize()){
            series?.let {
                item {
                    SeriesRecycle(it)
                }
            }

        }
    }
}

@Composable
fun SeriesRecycle(series: List<Series>) {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
        TitleSection(title = "Series") {

        }
        LazyRow{
            item {
                Spacer(modifier = Modifier.width(12.dp))
            }
            items(series){
                SeriesItem(series = it)
            }
        }

    }
}

@Composable
fun SeriesItem(series: Series){
    Card(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.Spacing.tiny)
            .height(130.dp)
            .width(130.dp),
        shape = RoundedCornerShape(8.dp)
    ){
        Box(Modifier.fillMaxSize()){
            AsyncImage(
                model= series.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                           colors = listOf(Color(0xB3F5F2F4), Color(0x80080708))
                        )
                    )
            ){
                Text(
                    text = series.title,
                    style =TextStyle(
                        fontSize = 12.sp
                    ),
                    modifier = Modifier
                        .padding(MaterialTheme.Spacing.small,MaterialTheme.Spacing.tiny)
                        .fillMaxWidth(),
                    maxLines = 1
                )
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
                color = MaterialTheme.Colors.primaryShadeLight
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
                color = MaterialTheme.Colors.secondaryColorLight
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
        backgroundColor = MaterialTheme.Colors.brandColor,
        contentColor = Color.White
    )
}