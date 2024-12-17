package com.example.claudiagalerapract2.domain.usecases.users

import com.example.claudiagalerapract2.data.UserRepository
import com.example.claudiagalerapract2.data.remote.di.modelo.NetworkResult
import com.example.claudiagalerapract2.domain.modelo.User
import javax.inject.Inject

class GetUser @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(key: Int): NetworkResult<User> {
        return repository.fetchUser(key)
    }
}

