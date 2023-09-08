package com.fantasy.fantasyfootball.ui.presentation.register

import com.fantasy.fantasyfootball.data.model.User

sealed class RegisterEvent {
    data class Register(val user: User): RegisterEvent()
}