package com.eraydel.autos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.eraydel.autos.adapter.AutosAdapter
import com.eraydel.autos.databinding.ActivityMainBinding
import com.eraydel.autos.db.Autos
import com.eraydel.autos.model.Auto

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAutos: ArrayList<Auto>
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.listar_autos)

        val dbAutos = Autos(this)
        listAutos = dbAutos.getAutos()

        if(listAutos.size == 0 ) binding.tvSinRegistros.visibility = View.VISIBLE
        else binding.tvSinRegistros.visibility = View.INVISIBLE

        val autosAdapter = AutosAdapter(this,listAutos)

        binding.lvAutos.adapter = autosAdapter

        binding.lvAutos.setOnItemClickListener { adapterView, view, i, l ->
            // l es el id e i es la posición
            val intent = Intent(this , DetailsActivity::class.java)
            intent.putExtra("id" , l.toInt())
            startActivity(intent)
            finish()
        }

    }



    fun click(view: View) {
        startActivity(Intent(this,InsertActivity::class.java))
        finish()
    }
}