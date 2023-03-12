package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.Player
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class FireStorePlayerRepository(private val ref: CollectionReference): PlayerRepository {
    override suspend fun getPlayersByArea(area: String): List<Player> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayerById(playerId: Int): Player? {
        val res = ref.document(playerId.toString()).get().await()
        return res.toObject(Player::class.java)?.copy(playerId = playerId)
    }

    override suspend fun createPlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(playerId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getPlayersBySearch(area: String, playerName: String): List<Player> {
        TODO("Not yet implemented")
    }

    override suspend fun sortPlayer(order: String, by: String, area: String): List<Player> {
        TODO("Not yet implemented")
    }

}