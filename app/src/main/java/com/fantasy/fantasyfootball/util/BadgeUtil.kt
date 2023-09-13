package com.fantasy.fantasyfootball.util

import android.widget.ImageView
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.core.Enums

object BadgeUtil {
    fun setTeamImage(teamName: String, imageView: ImageView) {
        val imageResId = when (teamName) {
            Enums.Team.Liverpool.name -> R.drawable.liverpool
            Enums.Team.ManUnited.name -> R.drawable.manutd
            Enums.Team.Arsenal.name -> R.drawable.arsenal
            Enums.Team.Chelsea.name -> R.drawable.chelsea
            else -> R.drawable.ic_soccer // add a default image if necessary
        }
        imageView.setImageResource(imageResId)
    }
}