package com.example.claudiagalerapract2.ui.listado

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Hero
import com.example.claudiagalerapract2.domain.usecases.peliculas.GetHeroes
import com.example.claudiagalerapract2.ui.pantallamain.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ListadoViewModel @Inject constructor(
    private val getHeroes: GetHeroes
) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        handleEvent(ListadoEvent.GetHeroes)
    }

    private fun obtenerHeroes() {
        viewModelScope.launch {
            val result = getHeroes()
            when (result) {
                is NetworkResult.Success -> {
                    val heroes = result.data ?: emptyList()
                    _uiState.value = MainState(heroes = heroes)

                }
                is NetworkResult.Error -> {
                    _uiState.value = MainState(heroes = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = MainState(heroes = emptyList())
                }
            }
        }
    }

    private fun deleteHeroe(hero: Hero) {
        val currentList = _uiState.value?.heroes?.toMutableList() ?: mutableListOf()
        currentList.remove(hero)
        _uiState.value = _uiState.value?.copy(heroes = currentList)
    }

    fun handleEvent(event: ListadoEvent) {
        when (event) {
            is ListadoEvent.GetHeroes -> obtenerHeroes()
            is ListadoEvent.DeleteHero -> deleteHeroe(event.hero)
            is ListadoEvent.UiEventDone -> {
            }
        }
    }
}
