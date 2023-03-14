package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class FireStoreUserRepository(
    private val auth: FirebaseAuth,
    private val ref: CollectionReference
) :
    UserRepository {
    override suspend fun getUserById(userId: Int): Flow<User?> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByUsername(username: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserCredentials(username: String, password: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(user: User): Long {
        TODO("Not yet implemented")
    }

    override suspend fun editUser(id: String, user: User): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(id: String, user: User) {
        val doc = ref.document(id)
        doc.set(user).await()
    }

    override suspend fun register(user: User): FirebaseUser? {
        val res = auth.createUserWithEmailAndPassword(user.email!!, user.password!!).await()

        if (res.user != null) {
//            ref.document(user.email).set(user).await()
            ref.add(user).await()
        }

        return res.user
    }

    override suspend fun login(email: String, password: String): Boolean {
        val res = auth.signInWithEmailAndPassword(email, password).await()

        return res.user?.uid != null
    }

    fun getUid(): String? {
        return auth.uid
    }

    suspend fun fetchUser(user: User): FirebaseUser? {
        val res = auth.createUserWithEmailAndPassword(user.email!!, user.password!!).await()

        return res.user
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

//    suspend fun getCurrentUser(): User? {
//        // this one returns based on the uuid that is the email
//        return auth.currentUser?.email?.let {
//            ref.document(it).get().await().toObject(User::class.java)
//        }
//    }

    suspend fun getCurrentUser(): User? {
        val email = auth.currentUser?.email
        var docId = ""
        val query = ref.whereEqualTo("email", email).get().await()
        query.documents.forEach {
            docId = it.id
        }
        return ref.document(docId).get().await().toObject(User::class.java)
    }
}