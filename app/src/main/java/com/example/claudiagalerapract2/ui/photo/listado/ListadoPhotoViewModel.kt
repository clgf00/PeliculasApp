package com.example.claudiagalerapract2.ui.photo.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.photos.DeletePhoto
import com.example.claudiagalerapract2.domain.usecases.photos.GetPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoPhotoViewModel @Inject constructor(
    private val getPhotos: GetPhotos,
    private val deletePhoto: DeletePhoto
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListadoStatePhoto())
    val uiState: StateFlow<ListadoStatePhoto> get() = _uiState.asStateFlow()

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
    private fun filtrarPhotos(query: String) {
        viewModelScope.launch {
            val allPhotos = getPhotos().let {
                if (it is NetworkResult.Success) it.data ?: emptyList() else emptyList()
            }
            val filteredPhotos = if (query.isEmpty()) {
                allPhotos
            } else {
                allPhotos.filter { it.title.contains(query, ignoreCase = true) }
            }

            _uiState.value = ListadoStatePhoto(photos = filteredPhotos)
        }
    }

    private fun eliminarPhoto(photoId: Int) {
        viewModelScope.launch {
            when (deletePhoto(photoId)) {
                is NetworkResult.Success -> {
                    obtenerPhotos()
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



    fun handleEvent(event: ListadoPhotoEvent) {
        when (event) {
            is ListadoPhotoEvent.DeletePhoto -> eliminarPhoto(event.photoId)
            is ListadoPhotoEvent.FilterPhotos -> filtrarPhotos(event.query)
            ListadoPhotoEvent.GetPhotos -> getPhotos
        }
    }
}