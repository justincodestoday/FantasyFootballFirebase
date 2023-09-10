package com.fantasy.fantasyfootball.presentation.ui.credentials.register

import com.fantasy.fantasyfootball.data.model.User

sealed class RegisterEvent {
    data class Register(val user: User): RegisterEvent()
}