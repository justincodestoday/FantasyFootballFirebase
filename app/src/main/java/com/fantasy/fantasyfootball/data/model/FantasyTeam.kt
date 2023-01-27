package com.fantasy.fantasyfootball.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

@Entity
data class FantasyTeam(
    @PrimaryKey(autoGenerate = true) val teamId: Int? = null,
    var name: String? = null,
    var points: Int = 0,
    var remainingBudget: Float = 0f,
    var lastUpdated: String? = null,
    val userId: Int,

//    @Embedded val user: User? = null,
//    @Relation(
//        parentColumn = "userId",
//        entityColumn = "ownerId"
//    )
//    val players: List<Player>,

//    var gk: FantasyPlayer? = null,
//    var lb: FantasyPlayer? = null,
//    var lcb: FantasyPlayer? = null,
//    var rcb: FantasyPlayer? = null,
//    var rb: FantasyPlayer? = null,
//    var lm: FantasyPlayer? = null,
//    var lcm: FantasyPlayer? = null,
//    var rcm: FantasyPlayer? = null,
//    var rm: FantasyPlayer? = null,
//    var ls: FantasyPlayer? = null,
//    var rs: FantasyPlayer? = null
)