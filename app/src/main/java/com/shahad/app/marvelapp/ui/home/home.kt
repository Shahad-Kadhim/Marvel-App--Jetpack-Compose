package com.shahad.app.marvelapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shahad.app.marvelapp.R
import com.shahad.app.marvelapp.domain.models.Creator
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
        val creators by viewModel.creators.observeAsState()

        LazyColumn(modifier = Modifier.fillMaxSize()){
            series?.let {
                item {
                    SeriesRecycle(it)
                }
            }
            item { Spacer(modifier = Modifier.height(MaterialTheme.Spacing.small)) }
            creators?.let {
                item{
                    CreatorRecycle(it)
                }
            }

        }
    }
}

@Composable
fun CreatorRecycle(creators: List<Creator>) {
    HorizontalSection(
        sectionTitle = "Creators",
        items = creators
    ) {
        CreatorItem(it)
    }
}

@Composable
fun CreatorItem(creator: Creator) {
    Column(
        modifier = Modifier.padding(horizontal = MaterialTheme.Spacing.tiny)
    ) {
        //TODO change image
//        AsyncImage(
//            model = creator.imageUrl,
        Image(
            painter = painterResource(id = R.drawable.test_image),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.Spacing.small))
        Text(
            text = creator.name,
            fontSize = 12.sp,
            color = MaterialTheme.Colors.secondaryColorLight,
            modifier = Modifier
                .width(100.dp)
                .padding(horizontal = MaterialTheme.Spacing.medium),
            maxLines = 1,
            textAlign = TextAlign.Center
        )

    }

}

@Composable
fun SeriesRecycle(series: List<Series>) {
    HorizontalSection(
        sectionTitle = "Series",
        items = series,
    ){
        SeriesItem(series = it)
    }
}

@Composable
fun <T> HorizontalSection(
    sectionTitle: String,
    items: List<T>,
    itemContent : @Composable (T) -> Unit
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
            items(items,){
                itemContent(it)
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
                        .padding(MaterialTheme.Spacing.small, MaterialTheme.Spacing.tiny)
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