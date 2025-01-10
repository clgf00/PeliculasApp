    package com.example.claudiagalerapract2.ui.pantallalogin

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.claudiagalerapract2.data.UserRepository
    import com.example.claudiagalerapract2.domain.modelo.User
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch
    import javax.inject.Inject

    @HiltViewModel
    class LoginViewModel @Inject constructor(
        private val userRepository: UserRepository
    ) : ViewModel() {

        private val _user = MutableStateFlow<User?>(null)
        val user: StateFlow<User?> = _user
        fun login(username: String, password: String? = null) {
            viewModelScope.launch {
                val user = if (password == null) {
                    userRepository.getUserByUsername(username)
                } else {
                    userRepository.getUserByUsernameAndPassword(username, password)
                }
                _user.value = user
            }
        }

        fun register(user: User) {
            viewModelScope.launch {
                userRepository.insert(user)
            }
        }

        suspend fun getUser(username: String): User? {
            return userRepository.getUserByUsername(username)
        }
    }
