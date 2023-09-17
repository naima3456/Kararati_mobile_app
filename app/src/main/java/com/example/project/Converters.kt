package com.example.project

import androidx.room.TypeConverter
import java.time.LocalDate
import java.util.Date

class Converters {
        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
           return value?.let { Date(it) }
        }
        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
           return date?.let { it.time }
        }
}