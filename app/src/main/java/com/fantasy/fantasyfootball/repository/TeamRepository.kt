package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.TeamDao
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.TeamsWithPlayers
import com.fantasy.fantasyfootball.data.model.User

class TeamRepository(private val teamDao: TeamDao) {
    suspend fun getTeamById(teamId: Int): Team? {
        return teamDao.getTeamById(teamId)
    }

    suspend fun getTeamByOwnerId(ownerId: Int): Team? {
        return teamDao.getTeamByOwnerId(ownerId)
    }

    suspend fun createTeam(team: Team): Long {
        return teamDao.insert(team)
    }

    suspend fun createPlayer(fantasyPlayer: FantasyPlayer) {
        teamDao.insertFantasyPlayer(fantasyPlayer)
    }

    suspend fun editTeam(teamId: Int, team: Team) {
        teamDao.insert(team.copy(teamId = teamId))
    }

    suspend fun deletePlayer(fanPlayerId: Int) {
        teamDao.deleteFantasyPlayer(fanPlayerId)
    }

    suspend fun updateBudget(teamId: Int, budget: Float) {
        teamDao.updateBudget(teamId, budget)
    }

    suspend fun updatePoints(teamId: Int, points: Int) {
        teamDao.updatePoints(teamId, points)
    }

    suspend fun getTeamsWithPlayers(): List<TeamsWithPlayers> {
        return teamDao.getTeamsWithPlayers()
    }

    suspend fun getTeamWithPlayersByTeamId(teamId: Int): TeamsWithPlayers {
        return teamDao.getTeamWithPlayersByTeamId(teamId)
    }
}