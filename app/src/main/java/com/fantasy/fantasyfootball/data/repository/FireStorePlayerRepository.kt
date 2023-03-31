package com.fantasy.fantasyfootball.data.repository

import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Player
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.*

class FireStorePlayerRepository(private val ref: CollectionReference) : PlayerRepository {
    override suspend fun getPlayersByArea(
        area: String,
        existingPlayer: List<String>
    ): List<Player> {
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

    override suspend fun getPlayersBySearch(area: String, playerName: String): List<Player> {
        val query = ref.whereEqualTo("area", area).orderBy("lastName")
        val snapshot = query.get().await()
        val result = snapshot.toObjects(Player::class.java).filter {
            it.lastName?.lowercase(Locale.getDefault())!!
                .contains(playerName.lowercase(Locale.getDefault()))
        }.map { it.copy(playerId = it.playerId) }

        // Filter by first name as well
        val firstNameQuery = ref.whereEqualTo("area", area)
            .orderBy("firstName")
        val firstNameSnapshot = firstNameQuery.get().await()
        val firstNameResult = firstNameSnapshot.toObjects(Player::class.java).filter {
            it.firstName?.lowercase(Locale.getDefault())!!
                .contains(playerName.lowercase(Locale.getDefault()))
        }.map { it.copy(playerId = it.playerId) }

        // Merge the two results and return the distinct list
        return (result + firstNameResult).distinctBy { it.playerId }
    }

    override suspend fun sortPlayer(order: String, by: String, area: String): List<Player> {
        val query = ref.whereEqualTo("area", area)
        val orderByField = if (by == Enums.SortBy.Price.name) "price" else "firstName"
        val sortOrder = if (order == Enums.SortOrder.Ascending.name) Query.Direction.ASCENDING else Query.Direction.DESCENDING
        val snapshot = query.orderBy(orderByField, sortOrder).get().await()
        return snapshot.toObjects(Player::class.java)
    }
}