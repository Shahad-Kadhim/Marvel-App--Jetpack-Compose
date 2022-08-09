package com.shahad.app.ui

import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.*
import com.shahad.app.core.models.Character
import com.shahad.app.core.models.Creator
import com.shahad.app.core.models.Series
import com.shahad.app.ui.theme.Spacing


@Composable
fun SeriesItem(
    series: Series,
    onClickFavorite: () -> Unit
){
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
                contentScale = ContentScale.Crop
            )
            Image(
                painter =
                    if (series.isFavourite) {
                        painterResource(id = R.drawable.ic_baseline_favorite_24)
                    }else{
                        painterResource(id = R.drawable.ic_favouraite)
                    }
                ,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(MaterialTheme.Spacing.medium)
                    .size(24.dp)
                    .clickable {
                        onClickFavorite()
                    }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color(0x99000000))
            ){
                Text(
                    text = series.title,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.White
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
            clipSpec = LottieClipSpec.Progress(0.5f, 0.75f),
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
fun NotFoundAnimation(){
    BasicLottie(lottieId = R.raw.not_found)
}

@Composable
fun SearchAnimation(){
    BasicLottie(lottieId = R.raw.search)
}


@Composable
fun CharacterItem(
    character: Character,
    onCLickItem: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.Spacing.medium,
                vertical = MaterialTheme.Spacing.tiny
            )
            .fillMaxWidth()
            .clickable {
                onCLickItem()
            }
    ){
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.secondary)
                .fillMaxWidth()
                .height(100.dp)
                .padding(MaterialTheme.Spacing.medium)
                .padding(end = 100.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = character.name,
                fontSize = 14.sp,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.Spacing.tiny))

            Text(
                text = character.description.takeIf{it.isNotBlank()} ?: "No Description Available",
                fontSize = 12.sp,
                maxLines = 2,
            )
        }
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier
                .padding(end = MaterialTheme.Spacing.large, bottom = MaterialTheme.Spacing.medium)
                .height(104.dp)
                .width(86.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(8.dp)),
            contentScale = ContentScale.FillBounds
        )
    }
}


@Composable
fun CreatorItem(creator: Creator) {
    Column(
        modifier = Modifier.padding(horizontal = MaterialTheme.Spacing.tiny).fillMaxSize()
    ) {
        AsyncImage(
            model = creator.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(MaterialTheme.Spacing.medium)
                .aspectRatio(1f)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(MaterialTheme.Spacing.small))
        Text(
            text = creator.name,
            fontSize = 12.sp,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .width(100.dp)
                .padding(horizontal = MaterialTheme.Spacing.medium),
            maxLines = 1,
            textAlign = TextAlign.Center
        )

    }

}
