package com.example.claudiagalerapract2.ui.pantallalogin

import androidx.lifecycle.ViewModel
import com.example.claudiagalerapract2.data.UserRepository
import com.example.claudiagalerapract2.domain.modelo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun login(id: Int): StateFlow<User?> {
        userRepository.getUserById(id) { user ->
            _user.value = user
        }
        return _user
    }
}
