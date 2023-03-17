package com.fantasy.fantasyfootball.repository

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

//    suspend fun removePlayer(fantasyPlayer: FantasyPlayer)
}