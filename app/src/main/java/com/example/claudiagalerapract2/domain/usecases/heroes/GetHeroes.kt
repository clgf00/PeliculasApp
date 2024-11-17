package com.example.claudiagalerapract2.domain.usecases.heroes

import com.example.claudiagalerapract2.data.HeroRepository
import javax.inject.Inject

class GetHeroes @Inject constructor(private val heroRepository: HeroRepository) {
    suspend operator fun invoke() = heroRepository.fetchHeroes()
}