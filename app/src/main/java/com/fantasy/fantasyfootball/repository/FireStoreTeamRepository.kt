package com.fantasy.fantasyfootball.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.Team
import com.google.firebase.firestore.CollectionReference

class FireStoreTeamRepository(private val ref: CollectionReference) : TeamRepository {
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

    override suspend fun registerTeam(
        team: Team,
        teamName: String,
        callback: (message: String) -> Unit
    ) {
        ref.whereEqualTo("name", teamName).get().addOnSuccessListener { querySnapshot ->
            if (querySnapshot.isEmpty) {
                ref.add(team).addOnSuccessListener {
                    Log.d(TAG, "New team created with ID: ${it.id}")
                    callback("No duplicates")
                }.addOnFailureListener {
                    Log.e(TAG, "Error creating new team", it)
                    callback("Error creating new team")
                }
            } else {
                Log.d(TAG, "Team with name '$teamName' already exists")
                callback(Enums.FormError.TEAM_NAME_EXISTS.name)
            }
        }.addOnFailureListener {
            Log.e(TAG, "Error querying for team document", it)
        }
    }
}