package com.example.claudiagalerapract2.domain.usecases.users

import com.example.claudiagalerapract2.data.UserRepository
import javax.inject.Inject

class GetUsers @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.fetchUsers()
}