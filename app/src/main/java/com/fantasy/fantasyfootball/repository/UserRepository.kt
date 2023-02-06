package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.UserDao
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam

class UserRepository(private val userDao: UserDao) {
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun getUserCredentials(username: String, password:String): User? {
        return userDao.getUserCredentials(username, password)
    }

//    fun isValidUser(user: User): Flow<User?> {
//        return userDao.isValidUser(user.username!!, user.password!!)
//    }

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