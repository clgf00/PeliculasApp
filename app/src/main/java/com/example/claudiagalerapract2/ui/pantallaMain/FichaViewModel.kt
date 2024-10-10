package com.example.claudiagalerapract2.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import com.example.claudiagalerapract2.domain.usecases.peliculas.AddPeliculaUseCase
import com.example.claudiagalerapract2.domain.usecases.peliculas.DeletePeliculaUseCase
import com.example.claudiagalerapract2.domain.usecases.peliculas.GetPeliculas
import com.example.claudiagalerapract2.domain.usecases.peliculas.UpdatePeliculaUseCase
import com.example.claudiagalerapract2.utils.StringProvider

class FichaViewModel(
    private val StringProvider: StringProvider,
    private val addPeliculaUseCase: AddPeliculaUseCase,
    private val deletePeliculaUseCase: DeletePeliculaUseCase,
    private val updatePeliculaUseCase: UpdatePeliculaUseCase,
    private val getPeliculas: GetPeliculas,
) : ViewModel() {

    private var indice = 0
    private val _uiState = MutableLiveData(FichaState())
    val uiState: LiveData<FichaState> get() = _uiState

    init {
        addPeliculaUseCase.invoke(Pelicula("", 0, "", "", false, 0))
        val peliculas = getPeliculas.invoke()
        _uiState.value = FichaState(
            pelicula = peliculas[indice],
            seekBarValue = peliculas[indice].calificacion,
            habilitarSiguiente = peliculas.size > 1

        )

    }

    fun addPelicula(pelicula: Pelicula) {
        if (pelicula.titulo.isNotEmpty() && pelicula.anyoEstreno != 0 && pelicula.genero?.isNotEmpty() == true) {
            addPeliculaUseCase(pelicula)
            _uiState.value = _uiState.value?.copy(
                pelicula = Pelicula("", 0, "", "", false, 0), // Restablecer la película vacía
                seekBarValue = 0, //
                habilitarSiguiente = false,
                habilitarAnterior = getPeliculas().isNotEmpty()
            )
        } else {
            _uiState.value = _uiState.value?.copy(error = Constantes.ERROR)
        }
    }


    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(error = null)
    }

    fun siguiente() {
        if (indice < getPeliculas().size - 1) {
            indice++
            _uiState.value = _uiState.value?.copy(
                pelicula = getPeliculas()[indice],
                seekBarValue = getPeliculas()[indice].calificacion,
                habilitarSiguiente = indice < getPeliculas().size
            )
        }
    }

    fun deletePelicula() {
        if (deletePeliculaUseCase(indice)) {
            if(indice==0){
                siguiente()
            }else{
                anterior()
            }
        } else {
            _uiState.value = _uiState.value?.copy(error = Constantes.ERROR)
        }
    }

    fun updatePelicula(pelicula: Any) {


    }

    fun anterior() {
        if (indice > 0) {
            indice--
            actualizarPelicula()
        }
    }

    private fun actualizarPelicula() {
        _uiState.value = _uiState.value?.copy(pelicula = getPeliculas()[indice])
        _uiState.value = _uiState.value?.copy(
            pelicula = getPeliculas()[indice],
            seekBarValue = getPeliculas()[indice].calificacion,
            habilitarAnterior = indice > 0,
            habilitarSiguiente = indice < getPeliculas().size - 1
        )
    }

    class MainViewModelFactory(
        private val stringProvider: StringProvider,
        private val addPelicula: AddPeliculaUseCase,
        private val deletePeliculaUseCase: DeletePeliculaUseCase,
        private val updatePeliculaUseCase: UpdatePeliculaUseCase,
        private val getPeliculas: GetPeliculas,

        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FichaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FichaViewModel(
                    stringProvider,
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