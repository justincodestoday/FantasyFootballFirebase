package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Player

interface PlayerRepository {
    suspend fun getPlayersByArea(area: String, existingPlayer: List<String>): List<Player>

    // Get player by id
    suspend fun getPlayerById(playerId: String): Player?

    // Insert a new player
    suspend fun createPlayer(player: Player)

    // Get players by searching for name
    suspend fun getPlayersBySearch(area: String, playerName: String): List<Player>

    // Sort players
    suspend fun sortPlayer(order: String, by: String, area: String): List<Player>
}