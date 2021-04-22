package com.example.student.zajecia2stgrupa2


import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_wiersz.view.*
import java.text.FieldPosition


class PokemonAdapter(val context: Activity,
                     val dane: Array<Pokemon>) : ArrayAdapter<Pokemon>(context, R.layout.pokemon_wiersz, dane) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater = context.layoutInflater
        var wiersz = inflater.inflate(R.layout.pokemon_wiersz, null, true)
        wiersz.pokeName.text = dane[position].nazwa
        wiersz.lblpokeType.text = dane[position].type
        Picasso.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+dane[position].id+".png").into(wiersz.pokeImg)
        return wiersz
    }

}