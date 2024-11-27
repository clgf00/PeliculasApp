package com.example.claudiagalerapract2.ui.pantalladetalle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.domain.usecases.todos.AddTodo
import com.example.claudiagalerapract2.domain.usecases.todos.DeleteTodo
import com.example.claudiagalerapract2.domain.usecases.todos.GetTodo
import com.example.claudiagalerapract2.domain.usecases.todos.UpdateTodo
import com.example.claudiagalerapract2.ui.common.Constantes
import com.example.claudiagalerapract2.ui.pantalladetalle.state.DetalleTodoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleTodoViewModel @Inject constructor(
    private val getTodo: GetTodo,
    private val addTodo: AddTodo,
    private val updateTodo: UpdateTodo,
    private val deleteTodo: DeleteTodo
) : ViewModel() {

    private val _uiState = MutableLiveData(DetalleTodoState())
    val uiState: LiveData<DetalleTodoState> get() = _uiState

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }

    fun cambiarTodo(id: Int) {
        viewModelScope.launch {
            when (val result = getTodo(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(todo = result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.CARGANDO)
                }
            }
        }
    }
    fun agregarTodo(todo: Todo) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ANYADIENDO)
            when (val result = addTodo(todo)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        mensaje = Constantes.ANYADIDO_EXITO,
                        todo = result.data
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }

    fun actualizarTodo(id: Int, todo: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ACTUALIZANDO)
            when (val result = updateTodo(id, todo)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(
                        mensaje = Constantes.ACTUALIZADO_EXITO,
                        todo = result.data
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {
                }
            }
        }
    }

    fun eliminarTodo(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ELIMINANDO)
            when (val result = deleteTodo(id)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ELIMINADO)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }
}

