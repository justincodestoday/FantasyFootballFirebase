package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.Team

interface TeamRepository {
    suspend fun getTeamById(teamId: Int): Team?

    suspend fun getTeamByOwnerId(ownerId: Int): Team?

    suspend fun createTeam(team: Team): Long

    suspend fun createPlayer(fantasyPlayer: FantasyPlayer)

    suspend fun editTeam(teamId: Int, team: Team)

    suspend fun deletePlayer(fanPlayerId: Int)

    suspend fun updateBudget(teamId: Int, budget: Float)

    suspend fun updatePoints(teamId: Int, points: Int)

    suspend fun registerTeam(team: Team, teamName: String, callback: (message: String) -> Unit)
}