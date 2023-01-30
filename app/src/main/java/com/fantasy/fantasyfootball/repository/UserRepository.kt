package com.fantasy.fantasyfootball.repository

import androidx.annotation.WorkerThread
import com.fantasy.fantasyfootball.data.UserDao
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    suspend fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

//    @WorkerThread
    suspend fun isValidUser(user: User): Flow<User?> {
        return userDao.isValidUser(user.username!!, user.password!!)
    }

    suspend fun createUser(user: User): Long {
        return userDao.createUser(user)
    }

    suspend fun deleteUser(userId: Int) {
        userDao.delete(userId)
    }
}