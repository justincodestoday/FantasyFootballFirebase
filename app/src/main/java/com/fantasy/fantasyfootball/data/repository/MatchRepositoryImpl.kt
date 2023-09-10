package com.fantasy.fantasyfootball.data.repository

import com.fantasy.fantasyfootball.data.model.Matches
import com.fantasy.fantasyfootball.domain.repository.MatchRepository
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class MatchRepositoryImpl(private val ref: CollectionReference): MatchRepository {

    override suspend fun getMatches(): List<Matches> {
        return ref.get().await().toObjects(Matches::class.java)
    }

    override suspend fun addMatches(matches: Matches) {
        val doc = ref.document()
        val id = doc.id
        val updatedMatches = matches.copy(matchId = id)
        doc.set(updatedMatches).await()
    }
}