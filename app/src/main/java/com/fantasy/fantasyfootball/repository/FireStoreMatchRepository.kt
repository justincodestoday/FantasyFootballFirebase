package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.Matches
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class FireStoreMatchRepository(private val ref: CollectionReference): MatchRepository {

    override suspend fun getMatches(): List<Matches> {
//        val querySnapshot = ref.get().await()
//        val matchesList = mutableListOf<Matches>()
//
//        for (document in querySnapshot.documents) {
//            val matches = document.toObject(Matches::class.java)
//            matches?.let {
//                matchesList.add(it)
//            }
//        }
//
//        return matchesList
        return ref.get().await().toObjects(Matches::class.java)
    }

    override suspend fun addMatches(matches: Matches) {
        val doc = ref.document()
        val id = doc.id
        val updatedMatches = matches.copy(matchId = id)
        doc.set(updatedMatches).await()
    }

    override suspend fun insert(match: Matches) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(matchId: Int) {
        TODO("Not yet implemented")
    }

    suspend fun addMatches(matches: Matches) {
        ref.add(matches).await()
    }
}