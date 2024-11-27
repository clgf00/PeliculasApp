package com.example.claudiagalerapract2.ui.listado.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.photos.DeletePhoto
import com.example.claudiagalerapract2.domain.usecases.photos.GetPhotos
import com.example.claudiagalerapract2.domain.usecases.posts.GetPosts
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.listado.events.ListadoPhotoEvent
import com.example.claudiagalerapract2.ui.listado.events.ListadoPostEvent
import com.example.claudiagalerapract2.ui.pantallamain.state.ListadoStatePhoto
import com.example.claudiagalerapract2.ui.pantallamain.state.ListadoStatePost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoPhotoViewModel @Inject constructor(
    private val getPhotos: GetPhotos,
    private val deletePhoto: DeletePhoto
) : ViewModel() {

    private val _uiState = MutableLiveData(ListadoStatePhoto())
    val uiState: LiveData<ListadoStatePhoto> get() = _uiState

    init {
        handleEvent(ListadoPhotoEvent.GetPhotos)
    }

    private fun obtenerPhotos() {
        viewModelScope.launch {
            when (val result = getPhotos()) {
                is NetworkResult.Success -> {
                    val users = result.data ?: emptyList()
                    _uiState.value = ListadoStatePhoto(photos = users)

                }

                is NetworkResult.Error -> {
                    _uiState.value = ListadoStatePhoto(photos = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStatePhoto(photos = emptyList())
                }
            }
        }
    }


    private fun eliminarPhoto(photoId: Int) {
        viewModelScope.launch {
            when (deletePhoto(photoId)) {
                is NetworkResult.Success -> {
                    val updatedPhotos = _uiState.value?.photos?.filter { it.id != photoId } ?: emptyList()
                    _uiState.value = ListadoStatePhoto(photos = updatedPhotos)

                }
                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }


    fun handleEvent(event: ListadoPhotoEvent) {
        when (event) {
            is ListadoPhotoEvent.GetPhotos -> obtenerPhotos()
            is ListadoPhotoEvent.DeletePhotos -> eliminarPhoto(event.photoId)
        }
    }
}