package com.example.claudiagalerapract2.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.claudiagalerapract2.R
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
        val peliculas = getPeliculas.invoke()
        _uiState.value = FichaState(
            pelicula = peliculas[indice],
            seekBarValue = peliculas[indice].calificacion
        )
    }

    fun addPelicula(pelicula: Pelicula) {
        //La función verifica si el resultado de addPersonaUseCase(persona) es falso
        // (lo que indica que no se pudo agregar la persona)
        //TODO: Cuando esté cambiando el valor del genero, seleccionar el RadioButton del RadioGroup, no cambiar el texto del RadioGroup
        if (!addPeliculaUseCase(pelicula)) {
            _uiState.value = FichaState(
                pelicula = uiState.value.let { pelicula },
                error = StringProvider.getString(R.string.pelicula)
            )
        } else {
            _uiState.value = _uiState.value?.copy(error = Constantes.ERROR)
            //Si el valor de _uiState no es null, se crea una nueva instancia de MainState
            // utilizando el contenido actual pero reemplazando el error con Constantes.ERROR.
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
                habilitarSiguiente = indice < getPeliculas().size
            )
        }
    }

    fun deletePelicula() {
        if (deletePeliculaUseCase(indice)) {
            anterior()

        } else {
            _uiState.value = _uiState.value?.copy(error = Constantes.ERROR)
        }
    }

    fun updatePelicula(pelicula: Any) {

    }

    fun anterior() {
        if (indice < getPeliculas().size - 1 && indice > 0) {
            indice--;
            _uiState.value = _uiState.value?.copy(
                pelicula = getPeliculas()[indice],
                habilitaAnterior = indice < getPeliculas().size - 1 && indice > 0
            )
        }
    }
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