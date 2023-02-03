package com.fantasy.fantasyfootball.util

import android.content.SharedPreferences
import android.util.Log
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.google.gson.Gson

class StorageService private constructor(
    private val sharedPref: SharedPreferences,
    private val gson: Gson
) {
//    fun setString(key: String, value: String) {
//        val editor = sharedPref.edit()
//        editor.putString(key, value)
//        editor.apply()
//    }
//
//    fun getString(key: String): String {
//        return sharedPref.getString(key, null) ?: ""
//    }
//
//    fun removeString(key: String) {
//        val editor = sharedPref.edit()
//        editor.remove(key)
//        editor.apply()
//    }

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

    fun updateUser(key: String, user: User) {
        val jsonString = gson.toJson(user)
        sharedPref.edit().putString(key, jsonString).apply()
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