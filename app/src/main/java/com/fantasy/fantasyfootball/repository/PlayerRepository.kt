package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.PlayerDao
import com.fantasy.fantasyfootball.data.model.Player

class PlayerRepository(private val playerDao: PlayerDao) {

    // Get all players
    suspend fun getPlayers(player: Player): List<Player> {
        return playerDao.getPlayers()
    }

    // Get player by id
    suspend fun getPlayerById(playerId: Int): Player? {
        return playerDao.getPlayerById(playerId)
    }

    // Insert a new player
    suspend fun insert(player: Player) {
        playerDao.insert(player)
    }

    // Delete a player
    suspend fun delete(playerId: Int) {
        playerDao.delete(playerId)
    }

    // Get players by searching for name
    suspend fun getPlayersBySearch(name: String): List<Player> {
        return playerDao.getPlayersBySearch(name)
    }
}