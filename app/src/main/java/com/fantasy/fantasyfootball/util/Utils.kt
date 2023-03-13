package com.fantasy.fantasyfootball.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH)
        val date = Date()
        val imageName = formatter.format(date)
        return imageName
    }
}