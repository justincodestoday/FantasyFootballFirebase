package com.fantasy.fantasyfootball.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fantasy.fantasyfootball.data.model.Matches

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches")
    suspend fun getMatches(): List<Matches>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: Matches)

    @Query("DELETE FROM matches WHERE matchId = :matchId")
    suspend fun delete(matchId: Int)
}