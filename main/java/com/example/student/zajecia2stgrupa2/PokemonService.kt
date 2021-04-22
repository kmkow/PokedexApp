package com.example.student.zajecia2stgrupa2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService  {
    @GET("pokemons")
    fun listaPokemonow():Call<List<Pokemon>>
    fun wyslij(@Path("id")id:Int, @Body pokemon: Pokemon):Call<Pokemon>
}