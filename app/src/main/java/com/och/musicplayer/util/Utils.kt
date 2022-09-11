package com.och.musicplayer.util

import java.util.concurrent.TimeUnit

object Utils {
    fun getStringTime(millis: Int?): String {
        var time = ""
        millis?.let { time = String.format("%02d : %02d",
            TimeUnit.MILLISECONDS.toMinutes(millis.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(millis.toLong()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis.toLong()))
        ) }
        return time
    }
}