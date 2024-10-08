package com.example.claudiagalerapract2.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.claudiagalerapract2.R
import com.example.claudiagalerapract2.domain.modelo.Pelicula
import com.example.claudiagalerapract2.domain.usecases.peliculas.AddPeliculaUseCase
import com.example.claudiagalerapract2.domain.usecases.peliculas.GetPeliculas
import com.example.claudiagalerapract2.utils.StringProvider

class FichaViewModel(
    private val StringProvider: StringProvider,
    private val addPeliculaUseCase: AddPeliculaUseCase,
    private val getPeliculas: GetPeliculas,
) : ViewModel() {

    private var indice = 0
    private val _uiState = MutableLiveData(FichaState())
    val uiState: LiveData<FichaState> get() = _uiState

    init {
        val peliculas = getPeliculas.invoke()
        _uiState.value = FichaState(pelicula = peliculas[indice],
            seekBarValue = peliculas[indice].calificacion)
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

    fun getPeliculas(id: Int) {
        val peliculas = getPeliculas.invoke()
        if (id >= peliculas.size || id < 0) {
            //si los ids van en orden, 1, 2, 3, 4..., entonces si se introduce un 5
            //debe dar error
            //esta linea revisa el valor del uiState, que es una instancia de MutableLiveData,
            //lo cual es un tipo de objeto observable que puede contener un valor
            // y notificar a los observadores cuando ese valor cambia.
            _uiState.value = _uiState.value?.copy(error = Constantes.ERROR)
        } else
            _uiState.value = _uiState.value?.copy(
                pelicula = peliculas[id],
                habilitarSiguiente = id < peliculas.size - 1,
            )
        //si el id es válido y no es null (?) y hay un id en la posición de la lista peliculas.size
        //se actualiza el estado de la interfaz del usuario y muestra la pelicula en cuestion.
    }

    fun getAllPeliculas(): List<String> {
        return getPeliculas().map { pelicula -> pelicula.titulo };
    }

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(error = null)
    }

    fun siguiente() {
        if (indice < getPeliculas().size - 1) {
            indice++;
            _uiState.value = _uiState.value?.copy(
                pelicula = getPeliculas()[indice],
                habilitarSiguiente = indice < getPeliculas().size - 1
            )
        }
    }
}

class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addPelicula: AddPeliculaUseCase,
    private val getPeliculas: GetPeliculas,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FichaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FichaViewModel(
                stringProvider,
                addPelicula,
                getPeliculas,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}