package com.fantasy.fantasyfootball.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.data.model.User

@Database(entities = [User::class, Team::class, Player::class], version = 4)
abstract class FantasyDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val playerDao: PlayerDao
    abstract val teamDao: TeamDao

    companion object {
        const val DATABASE_NAME = "fantasy_database"

        val MIGRATION_3_4 = object: Migration(3,4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("")
            }
        }
    }
}