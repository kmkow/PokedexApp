package com.example.student.zajecia2stgrupa2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_szczegoly.*

class SzczegolyActivity : AppCompatActivity() {
    var pokemon:Pokemon? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_szczegoly)
        pokemon = this.intent.extras?.get("pokemon") as Pokemon
        odswiez()

    }
    fun odswiez() {
        lblTyp.text = pokemon?.type
        lblNazwa.text = pokemon?.nazwa
        lblWeight.text = pokemon?.weight
        lblHeight.text = pokemon?.height
        var pokeUrl:String?=pokemon?.img
        Picasso.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemon?.id +".png").into(pokeImgSz)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                pokemon = data?.getSerializableExtra("pokemon") as Pokemon
                odswiez()
            }
        }
    }
}
