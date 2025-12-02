package com.example.pictureapi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pictureapi.API.GifData
import com.example.pictureapi.R
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
val paddingVal = 0.dp
val cardPadding = 4.dp
val cardElevation = 8.dp
val stringPadding = 16.dp
val minSize = 150.dp

@Composable
fun HomeScreen(
    photoUiState: PhotoUiState,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(paddingVal),
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var selectedPosition by rememberSaveable { mutableStateOf(0) }

    when (photoUiState) {
        is PhotoUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        //Сетка
        is PhotoUiState.Success -> {
            Box {
                PhotosGridScreen(
                    photos = photoUiState.photos.data,
                    modifier = modifier,
                    contentPadding = contentPadding,
                    onGifClick = {position ->
                        selectedPosition = position+1
                        showDialog = true
                    }
                )
                if (showDialog && (selectedPosition != 0)) {
                    AlertDialogByClick(selectedPosition, onDismiss = {showDialog = false})
                }
            }
        }
        is PhotoUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.load_pic)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.errorcircle),
            contentDescription = stringResource(R.string.error_description)
        )
        Text(text = stringResource(R.string.error_notification), modifier = Modifier.padding(stringPadding))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.error_button))
        }

    }
}

@Composable
fun PhotoCard(
    photo: GifData,
    position: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card (
        modifier = modifier
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.images.original.url)
                .decoderFactory(GifDecoder.Factory())
                //плавный переход между элементами или состояниями
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.errorcircle),
            placeholder = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.gif_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}

@Composable
fun PhotosGridScreen(
    photos: List<GifData>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(paddingVal),
    onGifClick: (Int) -> Unit
) {
    LazyVerticalStaggeredGrid (
        columns = StaggeredGridCells.Adaptive(minSize),
        modifier = modifier.padding(horizontal = cardPadding),
        contentPadding = contentPadding,
    ) {
        itemsIndexed(
            items = photos,
            key = {index, photo -> photo.id+index}
        ) { index, photo ->
            PhotoCard(
                photo = photo,
                position = index,
                modifier = modifier
                    .padding(cardPadding)
                    .fillMaxWidth(),
                onClick = { onGifClick(index) }
            )
        }
    }
}

@Composable
fun AlertDialogByClick(
    position: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.click_text, position))
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = stringResource(R.string.submit_button))
            }
        }
    )
}