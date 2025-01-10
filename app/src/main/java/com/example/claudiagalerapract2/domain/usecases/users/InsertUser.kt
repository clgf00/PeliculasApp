package com.example.claudiagalerapract2.domain.usecases.users

import com.example.claudiagalerapract2.data.UserRepository
import com.example.claudiagalerapract2.domain.modelo.User
import javax.inject.Inject

class InsertUser  @Inject constructor(val userRepository: UserRepository){
    suspend operator fun invoke(user: User) = userRepository.insert(user)
}