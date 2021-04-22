package com.example.student.zajecia2stgrupa2



import java.io.Serializable

data class Pokemon(

        val id:Int,
        var nazwa:String="",
        var img:String="",
        var type:String= "",
        var height:String = "",
        var weight:String =""


)  : Serializable
