package com.fantasy.fantasyfootball.repository

import android.util.Log
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class FireStoreFantasyRepository(private val ref: CollectionReference) : FantasyRepository {
    override suspend fun createPlayer(fantasyPlayer: FantasyPlayer) {
//        val email = auth.currentUser?.email
//        var docId = "default"
//        val query = ref.whereEqualTo("email", email).get().await()
//        query.documents.forEach {
//            docId = it.id
//        }
//        val newRef = Firebase.firestore.collection("fantasy")
//        newRef.document().set(fantasyPlayer).await()
        ref.document().set(fantasyPlayer).await()
        Log.d("debugging", "Pls be here")
    }
}