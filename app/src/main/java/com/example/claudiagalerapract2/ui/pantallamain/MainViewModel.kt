package com.example.claudiagalerapract2.ui.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.claudiagalerapract2.domain.usecases.peliculas.GetPeliculas

class MainViewModel(
    private val getPeliculas: GetPeliculas,
) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    private val _peliculas = MutableLiveData<MutableList<Pelicula>>(mutableListOf())
    val peliculas: MutableLiveData<MutableList<Pelicula>> get() = _peliculas
    val uiState: LiveData<MainState> get() = _uiState

    init {
        getPeliculas()
    }

    fun getPeliculas() {
        _uiState.value = _uiState.value?.copy(peliculas = getPeliculas.invoke())
    }
}

class MainViewModelFactory(

    private val getPeliculas: GetPeliculas,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                getPeliculas,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}