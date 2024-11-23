package com.example.claudiagalerapract2.ui.listado.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.usecases.users.GetUsers
import com.example.claudiagalerapract2.ui.listado.events.ListadoUserEvent
import com.example.claudiagalerapract2.ui.pantallamain.state.ListadoStateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListadoUserViewModel @Inject constructor(
    private val getUsers: GetUsers
) : ViewModel() {

    private val _uiState = MutableLiveData(ListadoStateUser())
    val uiState: LiveData<ListadoStateUser> get() = _uiState

    init {
        handleEvent(ListadoUserEvent.GetUsers)
    }

    private fun obtenerUsers() {
        viewModelScope.launch {
            when (val result = getUsers()) {
                is NetworkResult.Success -> {
                    val users = result.data ?: emptyList()
                    _uiState.value = ListadoStateUser(users = users)

                }
                is NetworkResult.Error -> {
                    _uiState.value = ListadoStateUser(users = emptyList())
                }

                is NetworkResult.Loading -> {
                    _uiState.value = ListadoStateUser(users = emptyList())
                }
            }
        }
    }

    fun handleEvent(event: ListadoUserEvent) {
        when (event) {
            is ListadoUserEvent.GetUsers -> obtenerUsers()
            ListadoUserEvent.AddUser -> TODO()
            ListadoUserEvent.DeleteUser -> TODO()
            ListadoUserEvent.UpdateUser -> TODO()
        }
        }
    }
