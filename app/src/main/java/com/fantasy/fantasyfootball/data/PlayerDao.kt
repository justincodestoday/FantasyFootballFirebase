package com.fantasy.fantasyfootball.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fantasy.fantasyfootball.data.model.Player

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    suspend fun getPlayers(): List<Player>

    @Query("SELECT * FROM player WHERE position = :position")
    suspend fun getPlayersByArea(position: String): List<Player>

    @Query("SELECT * FROM player WHERE playerId = :playerId")
    suspend fun getPlayerById(playerId: Int): Player?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: Player)

    @Query("DELETE FROM player WHERE playerId = :playerId")
    suspend fun delete(playerId: Int)

    @Query("UPDATE player SET isSet = :isSet WHERE playerId = :playerId")
    suspend fun updateStatusById(playerId: Int, isSet: Boolean)

    // making sure name works for first name + last name
    @Query("SELECT * FROM player WHERE position = :position AND (firstName LIKE '%' || :playerName || '%' OR lastName LIKE '%' || :playerName || '%')")
    suspend fun getPlayersBySearch(position: String, playerName: String): List<Player>
}