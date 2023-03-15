package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class FireStoreUserRepository(
    private val auth: FirebaseAuth,
    private val ref: CollectionReference
) :
    UserRepository {
    override suspend fun register(user: User): FirebaseUser? {
        val res = auth.createUserWithEmailAndPassword(user.email!!, user.password!!).await()

        if (res.user != null) {
            ref.add(user).await()
        }
        return res.user
    }

    override suspend fun login(email: String, password: String): Boolean {
        val res = auth.signInWithEmailAndPassword(email, password).await()

        return res.user?.uid != null
    }

    override suspend fun updateUser(user: User) {
        val email = auth.currentUser?.email
        var docId = ""
        val query = ref.whereEqualTo("email", email).get().await()
        query.documents.forEach {
            docId = it.id
        }
        val doc = ref.document(docId)
        doc.set(user).await()
    }

    override suspend fun registerTeam(
        email: String,
        team: Team
    ) {
        var docId = ""
        val query = ref.whereEqualTo("email", email).get().await()
        query.documents.forEach {
            docId = it.id
        }
        val doc = ref.document(docId)
        doc.update("team", team).await()
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

    suspend fun getCurrentUser(): User? {
        val email = auth.currentUser?.email
        var docId = "testId"
        val query = ref.whereEqualTo("email", email).get().await()
        query.documents.forEach {
            docId = it.id
        }
        return ref.document(docId).get().await().toObject(User::class.java)
    }

    fun getUid(): String? {
        return auth.uid
    }

    suspend fun fetchUser(user: User): FirebaseUser? {
        val res = auth.createUserWithEmailAndPassword(user.email!!, user.password!!).await()

        return res.user
    }
}