package com.example.claudiagalerapract2.ui.photopantalla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.apiServices.PhotoService
import com.example.claudiagalerapract2.data.remote.di.modelo.toPhoto
import com.example.claudiagalerapract2.domain.modelo.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoService: PhotoService
) : ViewModel() {

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> = _photos

    init {
        fetchPhotos()
    }

    private fun fetchPhotos() {
        viewModelScope.launch {
            try {
                val response = photoService.get()
                if (response.isSuccessful) {
                    _photos.value = response.body()?.map { it.toPhoto() } ?: emptyList()
                } else {
                    _photos.value = emptyList()
                }
            } catch (e: Exception) {
                _photos.value = emptyList()
            }
        }
    }


    fun loadAlbumPhotos(albumId: Int) {
        viewModelScope.launch {
            try {
                val response = photoService.getPhotosForAlbum(albumId)
                if (response.isSuccessful) {
                    _photos.value = response.body()?.map { it.toPhoto() } ?: emptyList()
                } else {
                    _photos.value = emptyList()
                }
            } catch (e: Exception) {
                _photos.value = emptyList()
            }
        }
    }
}
