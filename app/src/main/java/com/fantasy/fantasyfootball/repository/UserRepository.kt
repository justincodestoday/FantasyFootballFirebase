package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.UserDao
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    suspend fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    fun isValidUser(user: User): Flow<User?> {
        return userDao.isValidUser(user.username!!, user.password!!)
    }

    suspend fun createUser(user: User): Long {
        return userDao.insert(user)
    }

    suspend fun editUser(id: Int, user: User): Long {
        return userDao.insert(user.copy(userId = id))
    }

    suspend fun deleteUser(userId: Int) {
        userDao.delete(userId)
    }

    suspend fun getUsersWithTeams(): List<UserWithTeam> {
        return userDao.getUsersWithTeams()
    }

    suspend fun getUserWithTeam(userId: Int): UserWithTeam? {
        return userDao.getUserWithTeamByUserId(userId)
    }
}