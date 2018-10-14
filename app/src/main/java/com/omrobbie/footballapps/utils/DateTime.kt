package com.omrobbie.footballapps.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date?.toShortDate(): String {
    val formatter = SimpleDateFormat()
    formatter.applyPattern("dd MMMM yyyy")
    return formatter.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date?.toLongDate(): String {
    val formatter = SimpleDateFormat()
    formatter.applyPattern("EEE, dd MMM yyyy")
    return formatter.format(this)
}
