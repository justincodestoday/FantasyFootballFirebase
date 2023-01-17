package com.fantasy.fantasyfootball.data

import androidx.room.*
import com.fantasy.fantasyfootball.data.model.FantasyTeam
import com.fantasy.fantasyfootball.data.model.User

@Dao
interface UserDao {
//    @Transaction
//    @Query("SELECT * FROM user")
//    fun getUsersWithPlaylists(): List<FantasyTeam>

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}