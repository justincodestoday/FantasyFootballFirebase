package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun updateUser(id: String, user: User)

    suspend fun register(user: User): FirebaseUser?

    suspend fun login(email: String, password: String): Boolean
}