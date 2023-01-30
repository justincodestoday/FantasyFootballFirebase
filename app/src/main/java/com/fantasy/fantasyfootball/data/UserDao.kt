package com.fantasy.fantasyfootball.data

import androidx.room.*
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    fun isValidUser(username: String, password: String): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User): Long

    @Query("DELETE FROM user WHERE userId = :userId")
    suspend fun delete(userId: Int)

    @Transaction
    @Query("SELECT * FROM user")
    suspend fun getUsersWithTeams(): List<UserWithTeam>
}