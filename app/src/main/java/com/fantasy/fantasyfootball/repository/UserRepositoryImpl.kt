package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.UserDao
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun getUserById(userId: Int): Flow<User?> {
        return userDao.getUserById(userId)
    }

    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    override suspend fun getUserCredentials(username: String, password: String): User? {
        return userDao.getUserCredentials(username, password)
    }

    override suspend fun createUser(user: User): Long {
        return userDao.insert(user)
    }

    override suspend fun editUser(id: Int, user: User): Long {
        return userDao.insert(user.copy(userId = id))
    }

    override suspend fun getUsersWithTeams(): List<UserWithTeam> {
        return userDao.getUsersWithTeams()
    }

    override suspend fun getUserWithTeam(userId: Int): UserWithTeam? {
        return userDao.getUserWithTeamByUserId(userId)
    }

    override suspend fun register(user: User): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }
}