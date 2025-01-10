package com.example.claudiagalerapract2.ui.photo.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.photos.GetPhotos
import com.example.claudiagalerapract2.ui.common.Constantes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListadoPhotoViewModel @Inject constructor(
    private val getPhotos: GetPhotos,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListadoStatePhoto())
    val uiState: StateFlow<ListadoStatePhoto> get() = _uiState.asStateFlow()

    init {
        handleEvent(ListadoPhotoEvent.GetPhotos)
    }

    private fun obtenerPhotos() {
        viewModelScope.launch {
            getPhotos().collect { result ->

                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                photos = result.data,
                                isLoading = false
                            )
                        }
                    }

                    is NetworkResult.Error -> _uiState.update {
                        it.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }

                    is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }

                }
            }
        }
    }
    private fun filtrarPhotos(query: String) {
        viewModelScope.launch {
            getPhotos().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val allPhotos = result.data
                        val filteredPhotos = if (query.isEmpty()) {
                            allPhotos
                        } else {
                            allPhotos.filter { it.title.contains(query, ignoreCase = true) }
                        }
                        _uiState.value = ListadoStatePhoto(photos = filteredPhotos)
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = ListadoStatePhoto(
                            error = result.message,
                            photos = emptyList()
                        )
                    }
                    is NetworkResult.Loading -> {
                        _uiState.value = ListadoStatePhoto(isLoading = true)
                    }
                }
            }
        }
    }




    fun handleEvent(event: ListadoPhotoEvent) {
        when (event) {
            is ListadoPhotoEvent.FilterPhotos -> filtrarPhotos(event.query)
            ListadoPhotoEvent.GetPhotos -> obtenerPhotos()
        }
    }
}