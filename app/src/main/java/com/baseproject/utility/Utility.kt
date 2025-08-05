package com.baseproject.utility

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun convertTimeInMillisToStringDate(): String{
    val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.getDefault())
    val netDate = Date(System.currentTimeMillis())
    return sdf.format(netDate)

}