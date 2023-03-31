package com.fantasy.fantasyfootball.data.repository

import com.fantasy.fantasyfootball.data.model.Matches

interface MatchRepository {

    suspend fun getMatches(): List<Matches>

    suspend fun addMatches(matches: Matches)
}