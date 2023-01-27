package com.fantasy.fantasyfootball.data

import androidx.room.*
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun getUserByCredentials(username: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user WHERE userId = :userId")
    suspend fun delete(userId: Int)
}