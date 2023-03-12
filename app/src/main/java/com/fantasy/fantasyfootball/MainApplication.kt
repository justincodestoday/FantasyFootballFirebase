package com.fantasy.fantasyfootball

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.fantasy.fantasyfootball.data.FantasyDatabase
import com.fantasy.fantasyfootball.repository.MatchRepositoryImpl
import com.fantasy.fantasyfootball.repository.PlayerRepositoryImpl
import com.fantasy.fantasyfootball.repository.TeamRepositoryImpl
import com.fantasy.fantasyfootball.repository.UserRepositoryImpl
import com.fantasy.fantasyfootball.util.StorageService
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    lateinit var userRepo: UserRepositoryImpl
    lateinit var playerRepo: PlayerRepositoryImpl
    lateinit var teamRepo: TeamRepositoryImpl
    lateinit var matchRepo: MatchRepositoryImpl

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
        playerRepo = PlayerRepositoryImpl(fantasyDatabase.playerDao)
        teamRepo = TeamRepositoryImpl(fantasyDatabase.teamDao)
        matchRepo = MatchRepositoryImpl(fantasyDatabase.matchDao)


        val name: String = this.packageName ?: throw NullPointerException("No package name found")
        storageService = StorageService.getInstance(
            this.getSharedPreferences(name, Context.MODE_PRIVATE),
            Gson()
        )
    }
}