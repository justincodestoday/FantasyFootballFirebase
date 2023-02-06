package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["teamId", "playerId"])
data class TeamsPlayersCrossRef(
    val teamId: Int,
    val playerId: Int
)
