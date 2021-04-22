package com.example.student.zajecia2stgrupa2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FotkaDao {
    @Query("SELECT * from fotka")
    fun pobierz():List<Fotka>
    @Insert
    fun wstaw(fotka: Fotka)
}