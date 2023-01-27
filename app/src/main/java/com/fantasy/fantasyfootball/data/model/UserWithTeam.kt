package com.fantasy.fantasyfootball.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTeam(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val team: Team
)
