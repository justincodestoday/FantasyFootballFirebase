package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.PlayerDao
import com.fantasy.fantasyfootball.data.model.Player

class PlayerRepository(private val playerDao: PlayerDao) {

//     Get all players
//    suspend fun getPlayers(area: String, playername: String): List<Player> {
//        if(playername == "") {
//            return playerDao.getPlayers()
//        }
//        return playerDao.getPlayersBySearch(area, playername)
//    }

    // Gets players by position
    suspend fun getPlayersByArea(area: String): List<Player> {
        return playerDao.getPlayersByArea(area)
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

    // Set/Unset a player
    suspend fun changePlayerStatus(id: Int, isSet: Boolean) {
        playerDao.updateStatusById(id, isSet)
    }

    // Get players by searching for name
    suspend fun getPlayersBySearch(area: String, playername: String): List<Player> {
        return playerDao.getPlayersBySearch(area, playername)
    }

    // Sort players
    suspend fun sortPlayer(order:String, by:String): List<Player> {
        if(order == "Ascending" && by == "Name") {
            val res = playerDao.getPlayers()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.firstName })
        }else if(order == "Descending" && by == "Name") {
            val res = playerDao.getPlayers()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.firstName }).reversed()
        }else if(order == "Ascending" && by == "Price") {
            val res = playerDao.getPlayers()
            return res.sortedWith(compareBy(){it.price})
        } else if(order == "Descending" && by == "Price") {
            val res = playerDao.getPlayers()
            return res.sortedWith(compareBy(){it.price}).reversed()
        }
        return playerDao.getPlayers()
    }
}