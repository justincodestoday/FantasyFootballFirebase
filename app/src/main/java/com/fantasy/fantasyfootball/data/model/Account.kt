package com.fantasy.fantasyfootball.data.model

data class Account(
    val email: String? = null,
    val teamName: String? = null,
    val players: List<Player>? = null
)
