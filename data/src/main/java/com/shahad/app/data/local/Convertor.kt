package com.shahad.app.data.local

import androidx.room.TypeConverter
import java.util.*

internal class Convertor {

    @TypeConverter
    fun dateToLong(date: Date) = date.time

    @TypeConverter
    fun longToDate(long:Long) = Date(long)

}