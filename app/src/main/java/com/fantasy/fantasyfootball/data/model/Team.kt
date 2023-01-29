package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity
//    (
//    foreignKeys = arrayOf(
//        ForeignKey(
//            entity = User::class,
//            parentColumns = arrayOf("userId"),
//            childColumns = arrayOf("ownerId"),
//            onDelete = ForeignKey.CASCADE
//        )
//    )
//)
data class Team(
    @PrimaryKey(autoGenerate = true) var teamId: Int? = null,
    var name: String? = null,
    var points: Int = 0,
    var remainingBudget: Float = 0f,
    var lastUpdated: String? = null,
    val ownerId: Int,

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