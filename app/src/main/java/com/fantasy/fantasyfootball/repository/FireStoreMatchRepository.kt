package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.Matches
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class FireStoreMatchRepository(private val ref: CollectionReference): MatchRepository {
    override suspend fun getMatches(): List<Matches> {
//        val matches = mutableListOf<Matches>()
//        val res = ref.get().await()
//        for(document in res) {
//            matches.add(document.toObject(Matches::class.java).copy(matchId =))
//        }
//        return matches
        TODO("Not yet implemented")
    }

    override suspend fun insert(match: Matches) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(matchId: Int) {
        TODO("Not yet implemented")
    }
}