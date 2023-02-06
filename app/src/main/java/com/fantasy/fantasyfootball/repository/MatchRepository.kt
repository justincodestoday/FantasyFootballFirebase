package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.MatchDao
import com.fantasy.fantasyfootball.data.model.Matches

class MatchRepository(private val matchDao: MatchDao) {

    suspend fun getMatches(): List<Matches> {
        return matchDao.getMatches()
    }

    suspend fun insert(match: Matches) {
        matchDao.insert(match)
    }

    suspend fun delete(matchId: Int) {
        matchDao.delete(matchId)
    }
}