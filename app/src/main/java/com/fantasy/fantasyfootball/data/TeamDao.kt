package com.fantasy.fantasyfootball.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fantasy.fantasyfootball.data.model.Team

@Dao
interface TeamDao {
    @Query("SELECT * FROM team WHERE teamId = :teamId")
    suspend fun getTeamById(teamId: Int): Team?

    @Query("SELECT * FROM team WHERE ownerId = :ownerId")
    suspend fun getTeamByOwnerId(ownerId: Int): Team?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: Team)

    @Query("DELETE FROM team WHERE teamId = :teamId")
    suspend fun delete(teamId: Int)
}