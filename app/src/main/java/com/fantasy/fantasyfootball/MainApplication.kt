package com.fantasy.fantasyfootball

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.fantasy.fantasyfootball.data.FantasyDatabase
import com.fantasy.fantasyfootball.repository.PlayerRepository
import com.fantasy.fantasyfootball.repository.UserRepository
import com.fantasy.fantasyfootball.util.StorageService
import com.google.gson.Gson

class MainApplication : Application() {
     lateinit var userRepo: UserRepository
     lateinit var playerRepo: PlayerRepository
    lateinit var storageService: StorageService

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

        val name: String = this.packageName ?: throw NullPointerException("No package name found")
        storageService = StorageService.getInstance(
            this.getSharedPreferences(name, Context.MODE_PRIVATE),
            Gson()
        )
    }
}