package com.fantasy.fantasyfootball.repository

import com.fantasy.fantasyfootball.data.UserDao
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userDao: UserDao) {
    fun getUserById(userId: Int): Flow<User?> {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun getUserCredentials(username: String, password:String): User? {
        return userDao.getUserCredentials(username, password)
    }

    suspend fun createUser(user: User): Long {
        return userDao.insert(user)
    }

    suspend fun editUser(id: Int, user: User): Long {
        return userDao.insert(user.copy(userId = id))
    }

    suspend fun getUsersWithTeams(): List<UserWithTeam> {
        return userDao.getUsersWithTeams()
    }

    suspend fun getUserWithTeam(userId: Int): UserWithTeam? {
        return userDao.getUserWithTeamByUserId(userId)
    }

//    suspend fun register(user: User) {
//        if(user.username != null && user.password != null)  {
//            val res = auth.createUserWithEmailAndPassword(user.username, user.password).await()
//
//            if (res.user != null) {
//                ref.document(user.username).set(user).await()
//            }
//        }
//    }
//
//    suspend fun login(username: String, password: String): Boolean? {
//        val res = auth.signInWithEmailAndPassword(username, password).await()
//        return res.user?.uid != null
//    }
//
//    fun isAuthenticate(): Boolean {
//        val user = auth.currentUser
//        if(user == null) {
//            return false
//        }
//        return true
//    }
//
//    fun deAuthenticate() {
//        auth.signOut()
//    }
//
//    suspend fun getCurrentUser(): User? {
//        return auth.currentUser?.email?.let {
//            ref.document(it).get().await().toObject(User::class.java)
//        }
//    }

}