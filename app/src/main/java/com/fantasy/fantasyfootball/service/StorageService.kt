package com.fantasy.fantasyfootball.service

import android.content.SharedPreferences
import com.fantasy.fantasyfootball.data.model.User
import com.google.gson.Gson

class StorageService private constructor(
    private val sharedPref: SharedPreferences,
    private val gson: Gson
) {
    fun setUser(key: String, user: User) {
        val editor = sharedPref.edit()
        val jsonString = gson.toJson(user)
        editor.putString(key, jsonString)
        editor.apply()
    }

    fun getUser(key: String): User? {
        val jsonString = sharedPref.getString(key, "null")
        return gson.fromJson(jsonString, User::class.java)
    }

    fun removeUser(key: String) {
        val editor = sharedPref.edit()
        editor.remove(key)
        editor.apply()
    }

    companion object {
        private var storageService: StorageService? = null

        fun getInstance(sharedPref: SharedPreferences, gson: Gson): StorageService {
            if (storageService == null) {
                storageService = StorageService(sharedPref, gson)
            }

            return storageService!!
        }
    }
}