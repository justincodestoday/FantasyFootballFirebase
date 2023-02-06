package com.fantasy.fantasyfootball.data

import androidx.room.*
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.data.model.UserWithTeam
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("Select * FROM user Where userId = :userId")
    fun getUserById(userId: Int): Flow<User?>

    @Query("Select * FROM user Where username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUserCredentials(username: String, password: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("DELETE FROM user WHERE userId = :userId")
    suspend fun delete(userId: Int)

    @Transaction
    @Query("SELECT * FROM user")
    suspend fun getUsersWithTeams(): List<UserWithTeam>

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserWithTeamByUserId(userId: Int): UserWithTeam?
}