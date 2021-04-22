package com.example.student.zajecia2stgrupa2

import android.app.Activity
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fotka_layout.view.*

class GridAdapter(val context:Activity, val dane:Array<Fotka>) :
        ArrayAdapter<Fotka>(context, R.layout.fotka_layout, dane)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflater = context.layoutInflater
        var cellView:View? = null
        if (dane[position].id1 == -1) {
            cellView = inflater.inflate(R.layout.add_fotka_layout, null, true)
        } else {
            cellView = inflater.inflate(R.layout.fotka_layout, null, true)
            cellView?.obraz?.setImageURI(Uri.parse(dane[position].sciezka))
            cellView?.obraz?.adjustViewBounds = true
        }
        return cellView!!
    }
}