package com.example.claudiagalerapract2.ui.albumpantalla

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.claudiagalerapract2.domain.modelo.Album
import com.example.claudiagalerapract2.ui.common.Constantes

@Composable
fun AlbumItem(
    album: Album,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@Composable
fun AlbumsScreen(
    viewModel: AlbumViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onAlbumClick: (Int) -> Unit
) {
    val albums = viewModel.albums.collectAsStateWithLifecycle(initialValue = emptyList())

    Column(modifier = modifier.padding(paddingValues)) {
        Text(Constantes.ALBUMS)
        LazyColumn {
            items(albums.value) { album ->
                AlbumItem(album = album, onClick = { onAlbumClick(album.id) })
            }
        }
    }
}
