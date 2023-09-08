package com.fantasy.fantasyfootball.data.model

data class User(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val image: String? = null,
    val team: Team = Team()
)


