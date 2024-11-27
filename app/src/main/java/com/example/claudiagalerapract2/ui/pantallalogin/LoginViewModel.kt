package com.example.claudiagalerapract2.ui.pantallalogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    fun login(id: Int): LiveData<User?> {
        userRepository.getUserById(id) { user ->
            _user.value = user
        }
        return _user
    }
}
