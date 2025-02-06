package com.example.claudiagalerapract2.ui.photopantalla

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.claudiagalerapract2.domain.modelo.Photo
import com.example.claudiagalerapract2.ui.common.Constantes

@Composable
fun PhotoItem(photo: Photo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = photo.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun PhotosScreen(
    viewModel: PhotoViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    albumId: Int,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(albumId) {
        viewModel.loadAlbumPhotos(albumId)
    }

    val photos = viewModel.photos.collectAsStateWithLifecycle(initialValue = emptyList())

    Column(modifier = modifier.padding(paddingValues)) {
        Text(Constantes.PHOTOS)
        LazyColumn {
            items(photos.value) { photo ->
                PhotoItem(photo = photo)
            }
        }
    }
}
