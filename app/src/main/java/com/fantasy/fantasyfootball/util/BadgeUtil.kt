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
            Enums.Team.ManCity.name -> R.drawable.mancity
            Enums.Team.Newcastle.name -> R.drawable.newcastle
            Enums.Team.BorussiaDort.name -> R.drawable.dortmund
            Enums.Team.BayernMunich.name -> R.drawable.bayern
            Enums.Team.Barcelona.name -> R.drawable.barcelona
            Enums.Team.RealMadrid.name -> R.drawable.realmadrid
            Enums.Team.ACMilan.name -> R.drawable.acmilan
            Enums.Team.InterMilan.name -> R.drawable.intermilan
            else -> R.drawable.ic_soccer // add a default image if necessary
        }
        imageView.setImageResource(imageResId)
    }
}