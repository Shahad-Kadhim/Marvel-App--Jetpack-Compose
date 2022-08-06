package com.shahad.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shahad.app.core.models.Series
import com.shahad.app.ui.theme.Spacing


@Composable
fun SeriesItem(
    series: Series,
    onCLickItem: () -> Unit,
    onClickFavorite: () -> Unit
){
    Card(
        modifier = Modifier
            .padding(horizontal = MaterialTheme.Spacing.tiny)
            .height(130.dp)
            .width(130.dp)
            .clickable { onCLickItem() },
        shape = RoundedCornerShape(8.dp)
    ){
        Box(Modifier.fillMaxSize()){
            AsyncImage(
                model= series.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit
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
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xB3F5F2F4), Color(0x80080708))
                        )
                    )
            ){
                Text(
                    text = series.title,
                    style = TextStyle(
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