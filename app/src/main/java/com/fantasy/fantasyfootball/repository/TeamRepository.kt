package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.TeamDao
import com.fantasy.fantasyfootball.data.model.Team

class TeamRepository(private val teamDao: TeamDao) {
//    suspend fun getTeamById(teamId: Int): Team? {
//        return teamDao.getTeamById(teamId)
//    }
//
//    suspend fun getTeamByOwnerId(ownerId: Int): Team? {
//        return teamDao.getTeamByOwnerId(ownerId)
//    }

    suspend fun createTeam(team: Team) {
        teamDao.insert(team)
    }

    suspend fun editTeam(teamId: Int, team: Team) {
        teamDao.insert(team.copy(teamId = teamId))
    }
}