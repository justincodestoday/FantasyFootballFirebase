package com.fantasy.fantasyfootball.presentation.ui.credentials.login

import com.fantasy.fantasyfootball.data.model.User

sealed class LoginEvent {
    data class Login(val user: User): LoginEvent()
}
