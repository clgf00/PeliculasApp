package com.example.claudiagalerapract2.ui.listado

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import com.example.claudiagalerapract2.domain.usecases.peliculas.GetPeliculas
import com.example.claudiagalerapract2.ui.pantallamain.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ListadoViewModel @Inject constructor(
    private val getPeliculas: GetPeliculas
) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        handleEvent(ListadoEvent.GetPeliculas)
    }

    private fun obtenerPeliculas() {
        val peliculas = getPeliculas()
        _uiState.value = MainState(peliculas = peliculas)
    }

    private fun deletePelicula(pelicula: Pelicula) {
        val currentList = _uiState.value?.peliculas?.toMutableList() ?: mutableListOf()
        currentList.remove(pelicula)
        _uiState.value = _uiState.value?.copy(peliculas = currentList)
    }

    fun handleEvent(event: ListadoEvent) {
        when (event) {
            is ListadoEvent.GetPeliculas -> obtenerPeliculas()
            is ListadoEvent.DeletePelicula -> deletePelicula(event.pelicula)
            is ListadoEvent.UiEventDone -> {
            }
        }
    }
}
