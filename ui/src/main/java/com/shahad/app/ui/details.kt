package com.shahad.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shahad.app.core.DetailsScreenState
import com.shahad.app.core.models.Story
import com.shahad.app.ui.theme.Spacing
import com.shahad.app.viewmodels.CharacterDetailsViewModel

@Composable
fun CharacterDetailsScreen(
    navController: NavController,
    viewModel: CharacterDetailsViewModel,
    characterId: Long
){
    viewModel.getDetails(characterId)
    viewModel.getStories(characterId)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            val character by viewModel.character.observeAsState()
            val stories by viewModel.stories.observeAsState()
            HandleDetailsState(state = character) { character ->
                character?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(paddingValues)
                        ){
                            item{
                                AsyncImage(
                                    model = it.image,
                                    contentDescription = it.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.6f),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                            item{
                                Text(
                                    text = it.name,
                                    fontSize = 24.sp,
                                    modifier = Modifier.padding(
                                        horizontal = MaterialTheme.Spacing.medium,
                                        vertical = MaterialTheme.Spacing.medium
                                    )
                                )
                            }
                            item{
                                Text(
                                    text = it.description,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(horizontal = MaterialTheme.Spacing.medium)
                                )
                            }
                            if(stories is DetailsScreenState.Success) {
                                item{
                                    Text(
                                        text = "Stories",
                                        modifier = Modifier.padding(
                                            horizontal = MaterialTheme.Spacing.medium,
                                            vertical = MaterialTheme.Spacing.small
                                        ),
                                        fontSize = 24.sp
                                    )
                                }
                                val stories =
                                    (stories as DetailsScreenState.Success<List<Story>?>).data
                                stories?.let {
                                        items(it) {
                                            StoryItem(story = it)
                                        }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(MaterialTheme.Spacing.large)
                                .align(Alignment.TopStart)
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_back_arrow),
                                contentDescription = "go back",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable {
                                        navController.navigateUp()
                                    },
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }

                    }

                }
            }
        }
    )
}

@Composable
fun <T> HandleDetailsState(state: DetailsScreenState<T>?, onSuccess: @Composable (T?) -> Unit){
    when(state){
        is DetailsScreenState.Error -> ErrorConnectionAnimation()
        DetailsScreenState.Loading -> LoadingAnimation()
        is DetailsScreenState.Success -> onSuccess(state.data)
        null -> LoadingAnimation()
    }
}

@Composable
fun StoryItem(story: Story){
    Card(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.Spacing.medium,
                vertical = MaterialTheme.Spacing.small
            )
            .clip(RoundedCornerShape(MaterialTheme.Spacing.small))
            .fillMaxWidth() ,
        contentColor = MaterialTheme.colors.onSecondary,
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.Spacing.medium)
                .fillMaxWidth()

        ) {
            Text(text = story.title)
            Text(
                text = story.description,
                fontSize = 12.sp
            )
        }
        
        
    }
}