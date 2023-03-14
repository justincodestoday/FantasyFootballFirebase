package com.fantasy.fantasyfootball

import android.app.Application
import android.content.Context
import com.fantasy.fantasyfootball.service.StorageService
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    lateinit var storageService: StorageService

    override fun onCreate() {
        super.onCreate()

        val name: String = this.packageName ?: throw NullPointerException("No package name found")
        storageService = StorageService.getInstance(
            this.getSharedPreferences(name, Context.MODE_PRIVATE),
            Gson()
        )
    }
}