package com.fantasy.fantasyfootball.domain.usecase

import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.domain.repository.MatchesRepository
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository
) {
    suspend fun execute() {
//        return matchesRepository.getAllMatches.
    }
}