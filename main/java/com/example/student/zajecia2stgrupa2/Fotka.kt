package com.example.student.zajecia2stgrupa2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fotka (
        @PrimaryKey(autoGenerate = true) val id1:Int,
        @ColumnInfo(name="sciezka")val sciezka:String
)
{
}