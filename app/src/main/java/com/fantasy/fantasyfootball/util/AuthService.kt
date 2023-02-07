package com.fantasy.fantasyfootball.util

import android.content.Context
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.data.model.User

class AuthService (private val storageService: StorageService){
    fun authenticate(user: User) {
        storageService.setUser("user", user)
    }

    fun updateUser(user: User) {
        storageService.setUser("user", user)
    }

    fun unauthenticate() {
        storageService.removeUser("user")
    }

    fun isAuthenticated(): Boolean {
        val user = storageService.getUser("user")
        return user != null
    }

    fun getAuthenticatedUser(): User? {
        val user =  storageService.getUser("user")
        return user
    }

    companion object {
        private var authService: AuthService? = null

        fun getInstance(context: Context): AuthService {
            if (authService == null) {
                authService =
                    AuthService((context.applicationContext as MainApplication).storageService)
            }

            return authService!!
        }
    }
}