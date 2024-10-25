package com.example.claudiagalerapract2.ui.pantalladetalle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import com.example.claudiagalerapract2.domain.usecases.peliculas.AddPeliculaUseCase
import com.example.claudiagalerapract2.domain.usecases.peliculas.DeletePeliculaUseCase
import com.example.claudiagalerapract2.domain.usecases.peliculas.GetPeliculas
import com.example.claudiagalerapract2.domain.usecases.peliculas.UpdatePeliculaUseCase
import com.example.claudiagalerapract2.ui.Constantes

class DetalleViewModel(
    private val addPeliculaUseCase: AddPeliculaUseCase,
    private val deletePeliculaUseCase: DeletePeliculaUseCase,
    private val updatePeliculaUseCase: UpdatePeliculaUseCase,
    private val getPeliculas: GetPeliculas,
) : ViewModel() {

    private var indice = 0
    private val _uiState = MutableLiveData(DetalleState())
    val uiState: LiveData<DetalleState> get() = _uiState
    private var peliculas = getPeliculas.invoke()


        fun addPelicula(pelicula: Pelicula) {
            if (pelicula.titulo.isNotEmpty() && pelicula.anyoEstreno != 0 && pelicula.genero?.isNotEmpty() == true) {
                addPeliculaUseCase(pelicula)
            } else {
                _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
            }
        }

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    fun anyadidoMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    fun eliminadoMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    fun updateMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }


    fun deletePelicula() {
        if (deletePeliculaUseCase(indice)) {
            peliculas = getPeliculas.invoke()
            _uiState.value = _uiState.value?.copy(
                mensaje = Constantes.ELIMINADO,
                peliculas = peliculas
            )
        } else {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
        }
    }

    fun updatePelicula(pelicula: Pelicula) {
        if (pelicula.titulo.isNotEmpty() && pelicula.anyoEstreno != 0 && pelicula.genero?.isNotEmpty() == true) {
            updatePeliculaUseCase(pelicula, indice)
            peliculas = getPeliculas.invoke()
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ACTUALIZADO)


        } else {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
        }

    }

    fun cambiarPelicula(delta: Int) {
        indice = delta
        if (indice < 0) {
            indice = 0
        } else if (indice >= peliculas.size) {
            indice = peliculas.size - 1
        }

        _uiState.value = _uiState.value?.copy(
            pelicula = peliculas[delta],
            habilitarAnterior = indice > 0,
            habilitarSiguiente = indice < peliculas.size - 1
        )
    }

    class MainViewModelFactory(
        private val addPelicula: AddPeliculaUseCase,
        private val deletePeliculaUseCase: DeletePeliculaUseCase,
        private val updatePeliculaUseCase: UpdatePeliculaUseCase,
        private val getPeliculas: GetPeliculas,

        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetalleViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetalleViewModel(
                    addPelicula,
                    deletePeliculaUseCase,
                    updatePeliculaUseCase,
                    getPeliculas,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}