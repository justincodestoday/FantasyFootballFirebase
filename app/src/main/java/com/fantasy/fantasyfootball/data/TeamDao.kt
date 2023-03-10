package com.fantasy.fantasyfootball.data

import androidx.room.*
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.TeamsWithPlayers

@Dao
interface TeamDao {
    @Query("SELECT * FROM team WHERE teamId = :teamId")
    suspend fun getTeamById(teamId: Int): Team?

    @Query("SELECT * FROM team WHERE ownerId = :ownerId")
    suspend fun getTeamByOwnerId(ownerId: Int): Team?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: Team): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFantasyPlayer(fantasyPlayer: FantasyPlayer)

    @Query("UPDATE team SET budget = :budget WHERE teamId = :teamId")
    suspend fun updateBudget(teamId: Int, budget: Float)

    @Query("UPDATE team SET points = :points WHERE teamId = :teamId")
    suspend fun updatePoints(teamId: Int, points: Int)

    @Query("DELETE FROM team WHERE teamId = :teamId")
    suspend fun delete(teamId: Int)

    @Query("DELETE FROM FantasyPlayer WHERE fanPlayerId = :fanPlayerId")
    suspend fun deleteFantasyPlayer(fanPlayerId: Int)

    @Transaction
    @Query("SELECT * FROM team")
    suspend fun getTeamsWithPlayers(): List<TeamsWithPlayers>

    @Transaction
    @Query("SELECT * FROM team WHERE teamId = :teamId")
    suspend fun getTeamWithPlayersByTeamId(teamId: Int): TeamsWithPlayers
}