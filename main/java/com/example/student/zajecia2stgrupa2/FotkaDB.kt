package com.example.student.zajecia2stgrupa2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Fotka::class), version = 1)
abstract class FotkaDB : RoomDatabase() {
    abstract fun fotkaDao(): FotkaDao
}