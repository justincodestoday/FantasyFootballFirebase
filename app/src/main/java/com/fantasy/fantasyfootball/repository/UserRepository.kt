package com.fantasy.fantasyfootball.repository

import androidx.annotation.WorkerThread
import com.fantasy.fantasyfootball.data.UserDao
import com.fantasy.fantasyfootball.data.model.Team
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

    suspend fun getUserWithTeamByUserId(userId: Int): UserWithTeam? {
        return userDao.getUserByTeamByUserId(userId)
    }

//    @WorkerThread
    suspend fun isValidUser(user: User): User? {
        return userDao.isValidUser(user.username!!, user.password!!)
    }

    suspend fun createUser(user: User): Long {
        return userDao.insert(user)
    }

    suspend fun editUser(id: Int, user: User) {
        userDao.insert(user.copy(userId = id))
    }

    suspend fun deleteUser(userId: Int) {
        userDao.delete(userId)
    }
}