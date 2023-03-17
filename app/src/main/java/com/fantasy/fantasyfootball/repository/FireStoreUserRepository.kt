package com.fantasy.fantasyfootball.repository

import android.util.Log
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.*

class FireStoreUserRepository(
    private val auth: FirebaseAuth,
    private val ref: CollectionReference
) :
    UserRepository {
    override suspend fun register(user: User): FirebaseUser? {
        val res = auth.createUserWithEmailAndPassword(user.email!!, user.password!!).await()
//        val doc = ref.document()
//        val id = doc.id

        res.user?.let {
            ref.document(it.uid).set(user.copy(id = it.uid)).await()
        }
        return res.user
    }

    override suspend fun login(email: String, password: String): Boolean {
        val res = auth.signInWithEmailAndPassword(email, password).await()
        return res.user?.uid != null
    }

    override suspend fun updateUser(user: User) {
        val email = auth.currentUser?.email
        var docId = "default"
        val query = ref.whereEqualTo("email", email).get().await()
        query.documents.forEach {
            docId = it.id
        }
        val doc = ref.document(docId)
        doc.set(user).await()
    }

    override suspend fun addInfo(
        email: String,
        image: String,
        team: Team
    ) {
        var docId = "default"
        val query = ref.whereEqualTo("email", email).get().await()
        query.documents.forEach {
            docId = it.id
        }
        val doc = ref.document(docId)
        val user = doc.get().await().toObject(User::class.java)
        if (user != null) {
            doc.set(user.copy(image = image, team = team))
        }
    }

//    override suspend fun addPlayerToTeam(fantasyPlayer: FantasyPlayer) {
//        val email = auth.uid ?: ""
//        val user = ref.document(email)
//        val playerId = fantasyPlayer.copy(fanPlayerId = UUID.randomUUID().toString())
//        user.update("team.players", FieldValue.arrayUnion(playerId)).addOnSuccessListener {
//            Log.d("debugging", "success")
//        }.addOnFailureListener {
//            Log.d("debugging", "failed")
//        }
//    }

    override suspend fun addPlayerToTeam(fantasyPlayer: FantasyPlayer) {
        val email = auth.uid ?: ""
        val user = ref.document(email)
        val playerId = fantasyPlayer.copy(fanPlayerId = user.collection("team.players").document().id)
        user.update("team.players", FieldValue.arrayUnion(playerId)).addOnSuccessListener {
            Log.d("debugging", "success")
        }.addOnFailureListener {
            Log.d("debugging", "failed")
        }
    }

//    override suspend fun removePlayer(fanPlayerId: String) {
//        val email = auth.uid ?: ""
//        val user = ref.document(email)
//        val query = user.collection("team.players").whereEqualTo("fanPlayerId", fanPlayerId)
//        query.get().addOnSuccessListener { snapshot ->
//            snapshot.documents.forEach { document ->
//                user.update("team.players", FieldValue.arrayRemove(document.toObject(FantasyPlayer::class.java)))
//                    .addOnSuccessListener {
//                        Log.d("debugging", "success")
//                    }.addOnFailureListener {
//                        Log.d("debugging", "failed")
//                    }
//            }
//        }
//    }

    override suspend fun removePlayer(fanPlayerId: String) {
        val email = auth.uid ?: ""
        val user = ref.document(email)

        user.get().addOnSuccessListener { documentSnapshot ->
            val teamPlayers = documentSnapshot.get("team.players") as List<*>

            // Find the index of the player with the given fanPlayerId
            val playerIndex = teamPlayers.indexOfFirst { (it as Map<*, *>)["fanPlayerId"] == fanPlayerId }

            if (playerIndex != -1) {
                // Remove the player at the given index from the array
                val updatedPlayers = teamPlayers.toMutableList()
                updatedPlayers.removeAt(playerIndex)

                // Update the "team.players" field with the updated array
                user.update("team.players", updatedPlayers).addOnSuccessListener {
                    Log.d("debugging", "success")
                }.addOnFailureListener {
                    Log.d("debugging", "failed")
                }
            } else {
                Log.d("debugging", "Player with fanPlayerId $fanPlayerId not found")
            }
        }
    }

    override suspend fun updateBudget(budget: Float) {
        val email = auth.uid ?: ""
        val user = ref.document(email)
        user.update("team.budget", budget).addOnSuccessListener {
            Log.d("debugging", "success")
        }.addOnFailureListener {
            Log.d("debugging", "failed")
        }
    }

    override suspend fun updatePoints(points: Int) {
        val email = auth.uid ?: ""
        val user = ref.document(email)
        user.update("team.points", points).addOnSuccessListener {
            Log.d("debugging", "successful")
        }.addOnFailureListener {
            Log.d("debugging", "failure")
        }
    }

    suspend fun getCurrentUser(): User? {
        val email = auth.currentUser?.email
        var docId = "default"
        val query = ref.whereEqualTo("email", email).get().await()
        query.documents.forEach {
            docId = it.id
        }
        return ref.document(docId).get().await().toObject(User::class.java)
    }

    suspend fun getAllUsers(): List<User> {
        return ref.orderBy("team.points", Query.Direction.DESCENDING).get().await()
            .toObjects(User::class.java)
    }

    fun isAuthenticated(): Boolean {
        val user = auth.currentUser
        if (user == null) {
            return false
        }
        return true
    }

    fun deAuthenticate() {
        auth.signOut()
    }

    fun getUid(): String? {
        return auth.uid
    }
}
