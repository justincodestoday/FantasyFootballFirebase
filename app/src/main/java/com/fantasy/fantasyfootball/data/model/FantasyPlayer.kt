package com.fantasy.fantasyfootball.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fantasy.fantasyfootball.constant.Enums

//@Entity    (
//    foreignKeys = [ForeignKey(
//        entity = Team::class,
//        parentColumns = ["teamId"],
//        childColumns = ["fTeamId"],
//        onDelete = ForeignKey.CASCADE,
//        onUpdate = ForeignKey.CASCADE
//    )]
//)
//data class FantasyPlayer(
//    @PrimaryKey(autoGenerate = true) val fantasyId: Int? = null,
//    val fTeamId: Int,
//    val player: Player,
//    val position: Enums.Position,
//    val color: Enums.ShirtColor,
//    val isSet: Boolean
//)
