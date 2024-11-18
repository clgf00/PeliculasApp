package com.example.claudiagalerapract2.ui.listado

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.heroes.GetHeroes
import com.example.claudiagalerapract2.ui.pantallamain.ListadoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListadoViewModel @Inject constructor(
    private val getHeroes: GetHeroes
) : ViewModel() {

    private val _uiState = MutableLiveData(ListadoState())
    val uiState: LiveData<ListadoState> get() = _uiState

    init {
        handleEvent(ListadoEvent.GetHeroes)
    }

    private fun obtenerHeroes() {
        viewModelScope.launch {
            when (val result = getHeroes()) {
                is NetworkResult.Success -> {
                    val heroes = result.data ?: emptyList()
                    _uiState.value = ListadoState(heroes = heroes)

                }
                is NetworkResult.Error -> {
                    _uiState.value = ListadoState(heroes = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoState(heroes = emptyList())
                }
            }
        }
    }

    fun handleEvent(event: ListadoEvent) {
        when (event) {
            is ListadoEvent.GetHeroes -> obtenerHeroes()
            }
        }
    }
