package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Player

interface PlayerRepository {
    suspend fun getPlayersByArea(area: String): List<Player>

    // Get player by id
    suspend fun getPlayerById(playerId: Int): Player?

    // Insert a new player
    suspend fun createPlayer(player: Player)

    // Delete a player
    suspend fun delete(playerId: Int)

    // Get players by searching for name
    suspend fun getPlayersBySearch(area: String, playerName: String): List<Player>

    // Sort players

    suspend fun sortPlayer(order: String, by: String, area: String): List<Player> {
        var res = playerDao.getPlayersByArea(area)
        if (order == Enums.SortOrder.Ascending.name && by == Enums.SortBy.Name.name) {
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.firstName })
        } else if (order == Enums.SortOrder.Descending.name && by == Enums.SortBy.Name.name) {
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.firstName })
                .reversed()
        } else if (order == Enums.SortOrder.Ascending.name && by == Enums.SortBy.Price.name) {
            return res.sortedWith(compareBy { it.price })
        } else if (order == Enums.SortOrder.Descending.name && by == Enums.SortBy.Price.name) {
            return res.sortedWith(compareBy { it.price }).reversed()
        }
        return res
    }
}