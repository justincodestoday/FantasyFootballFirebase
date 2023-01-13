package com.fantasy.fantasyfootball

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.fantasy.fantasyfootball.repository.FantasyRepository
import com.google.gson.Gson

class MainApplication: Application() {
    val fantasyRepo = FantasyRepository.getInstance()
//    lateinit var fantasyRepo: FantasyRepository

//    override fun onCreate() {
//        super.onCreate()
//
//        val fantasyFootballDatabase = Room.databaseBuilder(
//            this,
//            FantasyFootballDatabase::class.java,
//            FantasyFootballDatabase.DATABASE_NAME
//        ).fallbackToDestructiveMigration()
//            .build()
//        fantasyRepo = FantasyRepository(fantasyFootballDatabase.fantasyDao)
//    }
}