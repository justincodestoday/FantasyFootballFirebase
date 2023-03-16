package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.Player
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class FireStorePlayerRepository(private val ref: CollectionReference): PlayerRepository {
    override suspend fun getPlayersByArea(area: String, existingPlayer: List<String>): List<Player> {
        val query = ref.whereEqualTo("area", area).get().await()
        return query.toObjects(Player::class.java).filterNot { player ->
            existingPlayer.any { it == player.lastName }
        }
    }

    override suspend fun getPlayerById(playerId: String): Player? {
        val res = ref.document(playerId).get().await()
        return res.toObject(Player::class.java)?.copy(playerId = playerId)
    }

    override suspend fun createPlayer(player: Player) {
        val doc = ref.document()
        val id = doc.id
        val updatedPlayers = player.copy(playerId = id)
        doc.set(updatedPlayers).await()
    }

    override suspend fun delete(playerId: String) {
        TODO("Not yet implemented")
    }

//    override suspend fun getPlayersBySearch(area: String, playerName: String): List<Player> {
//        val query = ref.whereEqualTo("area", area)
//            .whereGreaterThan("lastName", playerName)
//            .whereLessThan("lastName", playerName + "\uf8ff")
//            .orderBy("lastName")
//        val snapshot = query.get().await()
//        return snapshot.toObjects(Player::class.java).map { it.copy(playerId = it.playerId) }
//    }

    override suspend fun getPlayersBySearch(area: String, playerName: String): List<Player> {
        val query = ref.whereEqualTo("area", area)
            .whereGreaterThan("lastName", playerName)
            .whereLessThan("lastName", playerName + "\uf8ff")
            .orderBy("lastName")
        val snapshot = query.get().await()
        val result = snapshot.toObjects(Player::class.java).map { it.copy(playerId = it.playerId) }

        // Filter by first name as well
        val firstNameQuery = ref.whereEqualTo("area", area)
            .whereGreaterThan("firstName", playerName)
            .whereLessThan("firstName", playerName + "\uf8ff")
            .orderBy("firstName")
        val firstNameSnapshot = firstNameQuery.get().await()
        val firstNameResult = firstNameSnapshot.toObjects(Player::class.java).map { it.copy(playerId = it.playerId) }

        // Merge the two results and return the distinct list
        return (result + firstNameResult).distinctBy { it.playerId }
    }

    override suspend fun sortPlayer(order: String, by: String, area: String): List<Player> {
        TODO("Not yet implemented")
    }
}