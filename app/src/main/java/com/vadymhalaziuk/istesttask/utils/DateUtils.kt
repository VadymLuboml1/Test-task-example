package com.vadymhalaziuk.istesttask.utils

import java.time.DayOfWeek
import java.util.*

fun currentTimeStamp(): Long =
    System.currentTimeMillis()

/**
 * Return day of week starting from Sunday
 * */
fun currentDayOfWeek(): DayOfWeek =
    requireNotNull(
        DayOfWeek.values()
            .find {
                it.ordinal == Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            }
    )