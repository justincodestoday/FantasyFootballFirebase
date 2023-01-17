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

    @Query("SELECT * FROM player WHERE playerId = :playerId")
    suspend fun getPlayerById(playerId: Int): Player?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: Player)

    @Query("DELETE FROM player WHERE playerId = :playerId")
    suspend fun delete(playerId: Int)

    // making sure name works for first name + last name
    @Query("SELECT * FROM player WHERE name LIKE '%'|| :name ||'%'")
    suspend fun getPlayersBySearch(name: String): List<Player>
}