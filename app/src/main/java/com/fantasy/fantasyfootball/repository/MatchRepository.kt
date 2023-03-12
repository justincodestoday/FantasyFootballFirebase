package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.Matches

interface MatchRepository {

    suspend fun getMatches(): List<Matches>

    suspend fun insert(match: Matches)

    suspend fun delete(matchId: Int)

}