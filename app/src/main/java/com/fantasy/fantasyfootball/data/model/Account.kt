package com.fantasy.fantasyfootball.data.model

data class Account(
    val user: User? = null,
    val team: Team? = null,
    val players: List<Player>? = null
)
