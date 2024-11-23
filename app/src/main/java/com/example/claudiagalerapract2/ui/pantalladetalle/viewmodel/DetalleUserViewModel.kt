package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.users.GetUser
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetalleUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//F
@HiltViewModel
class DetalleUserViewModel @Inject constructor(
    private val getUser: GetUser,
) : ViewModel() {

    private val _uiState = MutableLiveData(DetalleUserState())
    val uiState: LiveData<DetalleUserState> get() = _uiState


    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)

    }

    fun cambiarUser(id: Int) {
        viewModelScope.launch {

            when (val result = getUser(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        user = result.data,
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Error al cargar usuario")
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = "Cargando usuario...")
                }
            }
        }
    }
}