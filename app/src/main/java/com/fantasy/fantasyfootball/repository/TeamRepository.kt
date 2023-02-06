package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.TeamDao
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.TeamsPlayersCrossRef
import com.fantasy.fantasyfootball.data.model.TeamsWithPlayers

class TeamRepository(private val teamDao: TeamDao) {
    suspend fun getTeamById(teamId: Int): Team? {
        return teamDao.getTeamById(teamId)
    }

//    suspend fun getTeamByOwnerId(ownerId: Int): Team? {
//        return teamDao.getTeamByOwnerId(ownerId)
//    }

    suspend fun createTeam(team: Team) {
        teamDao.insert(team)
    }

    suspend fun editTeam(teamId: Int, team: Team) {
        teamDao.insert(team.copy(teamId = teamId))
    }

    suspend fun addPlayers(teamsPlayersCrossRef: TeamsPlayersCrossRef) {
        teamDao.insertTeamsPlayersCrossRef(teamsPlayersCrossRef)
    }

    suspend fun updateBudget(teamId: Int, budget: Float) {
        teamDao.updateBudget(teamId, budget)
    }

    suspend fun getUsersWithTeams(): List<TeamsWithPlayers> {
        return teamDao.getTeamsWithPlayers()
    }

    suspend fun getTeamsWithPlayers(): List<TeamsWithPlayers> {
        return teamDao.getTeamsWithPlayers()
    }

    suspend fun getTeamWithPlayersByTeamId(teamId: Int): TeamsWithPlayers {
        return teamDao.getTeamWithPlayersByTeamId(teamId)
    }
}