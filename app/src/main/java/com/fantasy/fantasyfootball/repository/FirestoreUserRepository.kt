package com.fantasy.fantasyfootball.repository

import android.util.Log
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class FireStoreUserRepository(private val auth: FirebaseAuth, private val ref: CollectionReference): UserRepository {
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

    override suspend fun editUser(id: Int, user: User): Long {
        TODO("Not yet implemented")
    }

    override suspend fun getUsersWithTeams(): List<UserWithTeam> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserWithTeam(userId: Int): UserWithTeam? {
        TODO("Not yet implemented")
    }

    override suspend fun register(user: User): FirebaseUser? {
        val res = auth.createUserWithEmailAndPassword(user.username!!, user.password!!).await()

        if (res.user != null) {
            ref.document(user.username).set(user).await()
        }

        return res.user
    }

    override suspend fun login(email: String, password: String): Boolean {
        val res = auth.signInWithEmailAndPassword(email, password).await()

        return res.user?.uid != null
    }

    fun isAuthenticated(): Boolean {
        auth.currentUser ?: return false
        return true
    }

    fun deAuthenticate() {
        auth.signOut()
    }

    suspend fun getCurrentUser(): User? {
        return auth.currentUser?.email?.let {
            ref.document(it).get().await().toObject(User::class.java)
        }
    }
}