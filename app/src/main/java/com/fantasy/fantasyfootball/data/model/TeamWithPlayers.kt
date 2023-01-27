package com.fantasy.fantasyfootball.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TeamWithPlayers(
    @Embedded val team: Team,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "studentId",
        associateBy = Junction(TeamAndPlayersCrossRef::class)
    )
    val players: List<Player>
)
