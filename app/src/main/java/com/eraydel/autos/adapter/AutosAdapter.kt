package com.eraydel.autos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.eraydel.autos.R
import com.eraydel.autos.databinding.ListItemBinding
import com.eraydel.autos.model.Auto

class AutosAdapter (contexto: Context , listAutos: ArrayList<Auto>) : BaseAdapter() {

    private val listAutos = listAutos
    private val layoutInflater = LayoutInflater.from(contexto)


    override fun getCount(): Int {
        return listAutos.size
    }

    override fun getItem(p0: Int): Any {
        return listAutos[p0]
    }

    override fun getItemId(p0: Int): Long {
       return listAutos[p0].id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = ListItemBinding.inflate(layoutInflater)
        with(binding)
        {
            tvMarca.text = listAutos[p0].maker
            tvModelo.text = listAutos[p0].model
            when( listAutos[p0].maker )
            {
                "Tesla" -> {
                    ivMarca.setImageResource(R.drawable.tesla)
                }

                "Mazda" -> {
                    ivMarca.setImageResource(R.drawable.mazda)
                }

                "Toyota" -> {
                    ivMarca.setImageResource(R.drawable.toyota)
                }

                "Mercedes" -> {
                    ivMarca.setImageResource(R.drawable.mercedes)
                }
            }

        }

        return binding.root
    }

}