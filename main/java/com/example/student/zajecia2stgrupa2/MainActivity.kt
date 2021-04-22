package com.example.student.zajecia2stgrupa2

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_szczegoly.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.Manifest

class MainActivity : AppCompatActivity() {
    var nazwa = ""
    val danePrzykladowe = listOf(
            Pokemon(
            id=1,
            nazwa= "Pika",
            height= "",
            weight="",
            img=""
        )
    )
    var lista:ArrayList<Fotka> = ArrayList()
    var db:FotkaDB? = null


    var pokeDanePelne= listOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/kamKAN20/kamKAN20.github.io/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        var usluga = retrofit.create(PokemonService::class.java)
        var request = usluga.listaPokemonow()



        request.enqueue(object:
                Callback<List<Pokemon>>{
            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
            }

            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                pokeDanePelne = response.body()!!
                odswiez()
            }
        })
        pokemony.setOnItemClickListener { parent, view, position, id ->
            val pokemon:Pokemon = parent.getItemAtPosition(position) as Pokemon
            val intent:Intent = Intent(baseContext, SzczegolyActivity::class.java)
            intent.putExtra("pokemon", pokemon)
            startActivity(intent)
        }
        txtFiltr.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?)           {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nazwa = txtFiltr.text.toString()
                odswiez()
            }
        })
        odswiez()
        inicjalizujBaze()
    }



    //<<??>>



    fun odswiez() {
        var przefiltrowane = pokeDanePelne.filter {
            nazwa.isEmpty() || it.nazwa.contains(nazwa)
        }
        var adapter = PokemonAdapter(this, przefiltrowane.toTypedArray())
        pokemony.adapter = adapter
    }



    fun inicjalizujBaze() {
        this.db = Room.databaseBuilder(
                applicationContext,
                FotkaDB::class.java,
                "fotki"
        ).build()
        odswiezListe()
    }
    fun odswiezListe() {
        var runnable = Runnable {
            var fotki = db?.fotkaDao()?.pobierz()
            lista = ArrayList<Fotka>()
            lista.addAll(fotki!!)
            lista.add(Fotka(id1=-1, sciezka=""))
            val adapter = GridAdapter(this, lista.toTypedArray())
            grid.adapter = adapter
            grid.setOnItemClickListener { parent, view, position, id ->
                if (lista[position].id1 == -1) {
                    zrobZdjecie()
                }
            }
        }
        Thread(runnable).start()
    }

    fun zrobZdjecie() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var b1 = checkSelfPermission(Manifest.permission.CAMERA)
            var b2 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (b1 == PackageManager.PERMISSION_DENIED || b2 == PackageManager.PERMISSION_DENIED) {
                requestPermissions(
                        arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), 1000
                )
            } else {
                otworzKamere()
            }
        } else {
            otworzKamere()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1000) {
            if (grantResults.size == 2 &&
                    grantResults[0] == PackageManager
                            .PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager
                            .PERMISSION_GRANTED) {
                otworzKamere()
            } else {
                Toast.makeText(baseContext, "Błąd uprawnień!", Toast.LENGTH_LONG).show()
            }
        }
    }
    var imageUri: Uri? = null
    fun otworzKamere() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            var sciezka = imageUri.toString()
            var fotka = Fotka(id1 = 0, sciezka = sciezka)
            var runnable = Runnable {
                db?.fotkaDao()?.wstaw(fotka)
                odswiezListe()
            }
            Thread(runnable).start()
        }
    }
}

