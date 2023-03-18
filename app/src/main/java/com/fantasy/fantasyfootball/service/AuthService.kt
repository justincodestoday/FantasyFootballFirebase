package com.fantasy.fantasyfootball.service

import android.util.Log
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class AuthService(
    private val auth: FirebaseAuth,
    private val ref: CollectionReference
) {
    suspend fun register(user: User): FirebaseUser? {
        val res = auth.createUserWithEmailAndPassword(user.email!!, user.password!!).await()
        res.user?.let {
            ref.document(it.uid).set(user.copy(id = it.uid)).await()
        }
        return res.user
    }

    suspend fun login(email: String, password: String): Boolean {
        val res = auth.signInWithEmailAndPassword(email, password).await()
        return res.user?.uid != null
    }

    suspend fun updateUser(user: User) {
        auth.uid?.let { ref.document(it).set(user).await() }
    }

    suspend fun addInfo(
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

    fun addPlayerToTeam(fantasyPlayer: FantasyPlayer) {
        val email = auth.uid ?: ""
        val user = ref.document(email)
        val playerId =
            fantasyPlayer.copy(fanPlayerId = user.collection("team.players").document().id)
        user.update("team.players", FieldValue.arrayUnion(playerId))
    }

    fun removePlayer(fanPlayerId: String) {
        val email = auth.uid ?: ""
        val user = ref.document(email)

        user.get().addOnSuccessListener { documentSnapshot ->
            val teamPlayers = documentSnapshot.get("team.players") as List<*>

            // Find the index of the player with the given fanPlayerId
            val playerIndex =
                teamPlayers.indexOfFirst { (it as Map<*, *>)["fanPlayerId"] == fanPlayerId }

            if (playerIndex != -1) {
                // Remove the player at the given index from the array
                val updatedPlayers = teamPlayers.toMutableList()
                updatedPlayers.removeAt(playerIndex)

                // Update the "team.players" field with the updated array
                user.update("team.players", updatedPlayers)
            } else {
                Log.d("debugging", "Player with fanPlayerId $fanPlayerId not found")
            }
        }
    }

    fun updateBudget(budget: Float) {
        val email = auth.uid ?: ""
        ref.document(email).update("team.budget", budget)
    }

    fun updatePoints(points: Int) {
        val email = auth.uid ?: ""
        ref.document(email).update("team.points", points)
    }

    fun updatePassword(currentPassword: String, newPassword: String) {
        val user = auth.currentUser
        val credential = EmailAuthProvider.getCredential(user!!.email!!, currentPassword)
        user.reauthenticate(credential).addOnCompleteListener { authResult ->
            if (authResult.isSuccessful) {
                user.updatePassword(newPassword).addOnCompleteListener { updateResult ->
                    if (updateResult.isSuccessful) {
                        Timber.d("Password updated successfully")
                    } else {
                        Timber.e("Failed to update password: ${updateResult.exception?.message}")
                    }
                }
            } else {
                Timber.e("Failed to re-authenticate user: ${authResult.exception?.message}")
            }
        }
    }

    suspend fun getCurrentUser(): User? {
        val email = auth.uid ?: ""
        return ref.document(email).get().await().toObject(User::class.java)
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
}