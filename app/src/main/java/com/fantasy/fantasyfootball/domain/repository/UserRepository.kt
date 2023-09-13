package com.fantasy.fantasyfootball.domain.repository

import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.google.firebase.auth.FirebaseUser

interface UserRepository {
    suspend fun register(user: User): FirebaseUser?

    suspend fun login(email: String, password: String): Boolean

    suspend fun updateUser(user: User)

    suspend fun addInfo(email: String, image: String, team: Team)

    suspend fun addPlayerToTeam(fantasyPlayer: FantasyPlayer)

    suspend fun removePlayer(fanPlayerId: String)

    suspend fun updateBudget(budget: Float)

    suspend fun updatePoints(points: Int)

    suspend fun updatePassword(currentPassword: String, newPassword: String)

    suspend fun getCurrentUser(): User?

    suspend fun getAllUsers(): List<User>

    fun isAuthenticated(): Boolean

    fun deAuthenticate()
}