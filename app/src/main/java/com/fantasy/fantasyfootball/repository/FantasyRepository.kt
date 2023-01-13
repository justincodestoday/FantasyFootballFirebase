package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.model.FantasyPlayer
import com.fantasy.fantasyfootball.model.Player
import com.fantasy.fantasyfootball.model.User

class FantasyRepository {
    private var counter = -1
    private val userMap: MutableMap<Int, User> = mutableMapOf()
    private val playerMap: MutableMap<Int, Player> = mutableMapOf()
    private val fantasyPlayerMap: MutableMap<Int, FantasyPlayer> = mutableMapOf()

    fun getUsers(): List<User> {
        return userMap.values.toList()
    }

    fun getUserById(id: Int): User? {
        return userMap[id]
    }

    fun createUser(user: User): User? {
        userMap[++counter] = user.copy(id = counter)
        return userMap[counter]
    }

    fun getPlayers(name: String): List<Player> {
        return playerMap.filter { (key, value) ->
            Regex(
                name,
                RegexOption.IGNORE_CASE
            ).containsMatchIn(value.name)
        }.values.toList()
    }

    fun getPlayerById(id: Int): Player? {
        return playerMap[id]
    }

    fun addPlayer(player: Player): Player? {
        playerMap[++counter] = player.copy(id = counter)
        return playerMap[counter]
    }

    fun editPlayer(id: Int, player: Player): Player? {
        playerMap[id] = player
        return playerMap[id]
    }

    fun deletePlayer(id: Int) {
        playerMap.remove(id)
    }

    fun getFantasyPlayers(): List<FantasyPlayer> {
        return fantasyPlayerMap.values.toList()
    }

    fun getFantasyPlayerById(id: Int): FantasyPlayer? {
        return fantasyPlayerMap[id]
    }

    fun addFantasyPlayer(fantasyPlayer: FantasyPlayer): FantasyPlayer? {
        fantasyPlayerMap[++counter] = fantasyPlayer.copy(id = counter)
        return fantasyPlayerMap[counter]
    }

    fun setPlayer(id: Int): FantasyPlayer? {
        fantasyPlayerMap[id]?.isSet = !fantasyPlayerMap[id]?.isSet!!
        return fantasyPlayerMap[id]
    }

    companion object {
        private var fantasyRepository: FantasyRepository? = null
        fun getInstance(): FantasyRepository {
            if (fantasyRepository == null) {
                fantasyRepository = FantasyRepository()
            }

            return fantasyRepository!!
        }
    }
}