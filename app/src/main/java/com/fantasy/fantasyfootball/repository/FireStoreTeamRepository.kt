package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.TeamsWithPlayers
import com.google.firebase.firestore.CollectionReference

class FireStoreTeamRepository(private val ref: CollectionReference): TeamRepository {
    override suspend fun getTeamById(teamId: Int): Team? {
        TODO("Not yet implemented")
    }

    override suspend fun getTeamByOwnerId(ownerId: Int): Team? {
        TODO("Not yet implemented")
    }

    override suspend fun createTeam(team: Team): Long {
        TODO("Not yet implemented")
    }

    override suspend fun createPlayer(fantasyPlayer: FantasyPlayer) {
        TODO("Not yet implemented")
    }

    override suspend fun editTeam(teamId: Int, team: Team) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlayer(fanPlayerId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateBudget(teamId: Int, budget: Float) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePoints(teamId: Int, points: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTeamsWithPlayers(): List<TeamsWithPlayers> {
        TODO("Not yet implemented")
    }

    override suspend fun getTeamWithPlayersByTeamId(teamId: Int): TeamsWithPlayers {
        TODO("Not yet implemented")
    }

}