package com.fantasy.fantasyfootball.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.data.model.FantasyTeam
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.data.model.User

@Database(
    entities = [User::class, Player::class, FantasyTeam::class, FantasyPlayer::class],
    version = 1
)
abstract class FantasyDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val playerDao: PlayerDao

    companion object {
        const val DATABASE_NAME = "fantasy_database"
    }
}