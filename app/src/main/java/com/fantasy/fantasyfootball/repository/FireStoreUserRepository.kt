package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FireStoreUserRepository(
    private val auth: FirebaseAuth,
    private val ref: CollectionReference
) :
    UserRepository {
    override suspend fun register(user: User): FirebaseUser? {
        val res = auth.createUserWithEmailAndPassword(user.email!!, user.password!!).await()
        val doc = ref.document()
        val id = doc.id
        if (res.user != null) {
            ref.document(id).set(user.copy(id = id)).await()
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
