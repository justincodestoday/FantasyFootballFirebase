package com.fantasy.fantasyfootball.model

import androidx.room.PrimaryKey

data class User(
    @PrimaryKey
    val id: Int? = null,
    var username: String? = null,
    var password: String? = null,
    var fantasyTeam: FantasyTeam? = null
)
