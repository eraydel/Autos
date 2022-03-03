package com.eraydel.autos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.eraydel.autos.databinding.ActivityInsertBinding
import com.eraydel.autos.db.Autos

class InsertActivity : AppCompatActivity() , AdapterView.OnItemClickListener {

    private lateinit var binding: ActivityInsertBinding
    private var itemSelected : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString( R.string.agregar_auto)
        val makers = resources.getStringArray(R.array.listado_marcas)
        val adapter = ArrayAdapter(this@InsertActivity, R.layout.list_makers , makers )

        with(binding.tietMarca){
            setAdapter(adapter)
            onItemClickListener = this@InsertActivity
        }

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position)
        {
            0 -> {
                binding.ivMarcaSeleccionada.setImageResource(R.drawable.tesla)
            }
            1 -> {
                binding.ivMarcaSeleccionada.setImageResource(R.drawable.mazda)
            }
            2 -> {
                binding.ivMarcaSeleccionada.setImageResource(R.drawable.toyota)
            }
            3 -> {
                binding.ivMarcaSeleccionada.setImageResource(R.drawable.mercedes)
            }
        }
    }

    fun click(view: View) {
        val dbAutos = Autos(this)

        with(binding)
        {

            if(!tietMarca.text.toString().isEmpty() && !tietModelo.text.toString().isEmpty() && !tietAnio.text.toString().isEmpty() && !tietPrecio.text.toString().isEmpty()){
                val id = dbAutos.insertAuto( tietMarca.text.toString() , tietModelo.text.toString() , Integer.parseInt(tietAnio.text.toString()) , Integer.parseInt(tietPrecio.text.toString()) )
                if(id > 0 ) //if a new car added correctly
                {
                    Toast.makeText(this@InsertActivity , getString(R.string.mensaje_guardado) , Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this@InsertActivity,MainActivity::class.java))
                    finish()

                    /* cleaning all inputs */
                    tietMarca.setText("")
                    tietModelo.setText("")
                    tietAnio.setText("")
                    tietPrecio.setText("")

                } else {
                    Toast.makeText(this@InsertActivity , getString(R.string.error_baseDatos) , Toast.LENGTH_SHORT).show()
                }
            } else {

                if( tietMarca.text.toString() == "" ) {
                    tietMarca.error = getString(R.string.valor_requerido)
                }

                if( tietModelo.text.toString() == "" ) {
                    tietModelo.error = getString(R.string.valor_requerido)
                }

                if( tietAnio.text.toString() == "" ) {
                    tietAnio.error = getString(R.string.valor_requerido)
                }

                if( tietPrecio.text.toString() == "" ) {
                    tietPrecio.error = getString(R.string.valor_requerido)
                }

                Toast.makeText(this@InsertActivity , getString(R.string.llena_campos) , Toast.LENGTH_SHORT).show()

            }
        }
    }

    /* Se sobreescribe para regresar al main activity*/
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}