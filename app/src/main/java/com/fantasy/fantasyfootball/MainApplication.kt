package com.fantasy.fantasyfootball

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.fantasy.fantasyfootball.data.FantasyDatabase
import com.fantasy.fantasyfootball.repository.MatchRepository
import com.fantasy.fantasyfootball.repository.PlayerRepository
import com.fantasy.fantasyfootball.repository.TeamRepository
import com.fantasy.fantasyfootball.repository.UserRepositoryImpl
import com.fantasy.fantasyfootball.util.StorageService
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    lateinit var userRepo: UserRepositoryImpl
    lateinit var playerRepo: PlayerRepository
    lateinit var teamRepo: TeamRepository
    lateinit var matchRepo: MatchRepository
    lateinit var storageService: StorageService

    override fun onCreate() {
        super.onCreate()

        val fantasyDatabase = Room.databaseBuilder(
            this,
            FantasyDatabase::class.java,
            FantasyDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .addMigrations(FantasyDatabase.MIGRATION_1_2)
            .build()
        userRepo = UserRepositoryImpl(fantasyDatabase.userDao)
        playerRepo = PlayerRepository(fantasyDatabase.playerDao)
        teamRepo = TeamRepository(fantasyDatabase.teamDao)
        matchRepo = MatchRepository(fantasyDatabase.matchDao)

        val name: String = this.packageName ?: throw NullPointerException("No package name found")
        storageService = StorageService.getInstance(
            this.getSharedPreferences(name, Context.MODE_PRIVATE),
            Gson()
        )
    }
}