package com.fantasy.fantasyfootball.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TeamsWithPlayers(
    @Embedded val team: Team,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "playerId",
        associateBy = Junction(TeamsPlayersCrossRef::class)
    )
    val players: List<Player>
)
