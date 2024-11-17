package com.example.claudiagalerapract2.ui.pantalladetalle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.heroes.GetHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//F

@HiltViewModel
class DetalleViewModel @Inject constructor(
    private val getHero: GetHero,
) : ViewModel() {

    private val _uiState = MutableLiveData(DetalleState())
    val uiState: LiveData<DetalleState> get() = _uiState


    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)

    }

    fun cambiarHeroe(id: String) {
        viewModelScope.launch {

            when (val result = getHero(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        hero = result.data,
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Error al cargar héroe")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Cargando héroe...")
                }
            }
        }
    }
}