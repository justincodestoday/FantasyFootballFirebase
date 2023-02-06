package com.fantasy.fantasyfootball.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class TeamsWithPlayers(
    @Embedded val team: Team,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "teamOwnerId"
    )
    val players: List<Player>
)
