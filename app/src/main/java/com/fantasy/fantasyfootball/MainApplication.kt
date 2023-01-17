package com.fantasy.fantasyfootball

import android.app.Application
import androidx.room.Room
import com.fantasy.fantasyfootball.data.FantasyDatabase
import com.fantasy.fantasyfootball.repository.PlayerRepository
import com.fantasy.fantasyfootball.repository.UserRepository

class MainApplication : Application() {
    private lateinit var userRepo: UserRepository
    private lateinit var playerRepo: PlayerRepository

    override fun onCreate() {
        super.onCreate()

        val fantasyDatabase = Room.databaseBuilder(
            this,
            FantasyDatabase::class.java,
            FantasyDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
        userRepo = UserRepository(fantasyDatabase.userDao)
        playerRepo = PlayerRepository(fantasyDatabase.playerDao)
    }
}