package com.fantasy.fantasyfootball.util

import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentTeamManagementBinding

object TeamUtil {
    fun setImageForPosition(position: String, color: Int, binding: FragmentTeamManagementBinding) {
        when (position) {
            Enums.Position.GK.name -> binding.gk.setImageResource(color)
            Enums.Position.LB.name -> binding.lb.setImageResource(color)
            Enums.Position.LCB.name -> binding.lcb.setImageResource(color)
            Enums.Position.RCB.name -> binding.rcb.setImageResource(color)
            Enums.Position.RB.name -> binding.rb.setImageResource(color)
            Enums.Position.LM.name -> binding.lm.setImageResource(color)
            Enums.Position.LCM.name -> binding.lcm.setImageResource(color)
            Enums.Position.RCM.name -> binding.rcm.setImageResource(color)
            Enums.Position.RM.name -> binding.rm.setImageResource(color)
            Enums.Position.LS.name -> binding.ls.setImageResource(color)
            Enums.Position.RS.name -> binding.rs.setImageResource(color)
        }
    }

    fun setShirtColor(color: Enums.ShirtColor): Int {
        return when (color) {
            Enums.ShirtColor.LIGHTRED -> R.drawable.shirt_light_red
            Enums.ShirtColor.DARKRED -> R.drawable.shirt_dark_red
            Enums.ShirtColor.LIGHTBLUE -> R.drawable.shirt_light_blue
            Enums.ShirtColor.DARKBLUE -> R.drawable.shirt_dark_blue
            Enums.ShirtColor.YELLOW -> R.drawable.shirt_yellow
            Enums.ShirtColor.WHITE -> R.drawable.shirt_white
            Enums.ShirtColor.BLACK -> R.drawable.shirt_black
            Enums.ShirtColor.PURPLE -> R.drawable.shirt_pink
            else -> R.drawable.shirt2
        }
    }

    fun setPlayerName(position: String, lastName: String, binding: FragmentTeamManagementBinding) {
        when (position) {
            Enums.Position.GK.name -> binding.goalKeeper.text = lastName
            Enums.Position.LB.name -> binding.leftBack.text = lastName
            Enums.Position.LCB.name -> binding.leftCenterBack.text = lastName
            Enums.Position.RCB.name -> binding.rightCenterBack.text = lastName
            Enums.Position.RB.name -> binding.rightBack.text = lastName
            Enums.Position.LM.name -> binding.leftMidfielder.text = lastName
            Enums.Position.LCM.name -> binding.leftCenterMid.text = lastName
            Enums.Position.RCM.name -> binding.rightCenterMid.text = lastName
            Enums.Position.RM.name -> binding.rightMidfielder.text = lastName
            Enums.Position.LS.name -> binding.leftStriker.text = lastName
            Enums.Position.RS.name -> binding.rightStriker.text = lastName
        }
    }
}