package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.UserDao
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByCredentials(username: String, password: String): User? {
        return userDao.getUserByCredentials(username, password)
    }

    suspend fun createUser(user: User): Long {
        return userDao.createUser(user)
    }

    suspend fun deleteUser(userId: Int) {
        userDao.delete(userId)
    }
}