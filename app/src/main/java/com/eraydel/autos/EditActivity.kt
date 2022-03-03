package com.eraydel.autos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.eraydel.autos.databinding.ActivityEditBinding
import com.eraydel.autos.db.Autos
import com.eraydel.autos.model.Auto

class EditActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var binding: ActivityEditBinding
    private lateinit var dbAutos: Autos
    var auto: Auto? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if( savedInstanceState == null )
        {
            val bundle = intent.extras
            if(bundle != null){
                id = bundle.getInt("id" , 0)
            }
        }else {
            id = savedInstanceState.getSerializable("id") as Int
        }

        dbAutos = Autos(this)
        auto = dbAutos.getAuto(id)

        if(auto != null)
        {
            with(binding)
            {
                supportActionBar?.title = getString( R.string.editar_auto )
                tietMarca.setText(auto?.maker)
                tietModelo.setText(auto?.model)
                tietAnio.setText(auto?.year.toString())
                tietPrecio.setText(auto?.price.toString())

                if( tietMarca.text.toString() == resources.getText(R.string.opcion1) )
                {
                    ivMarcaSeleccionada.setImageResource(R.drawable.tesla)
                }
                if( tietMarca.text.toString() == resources.getText(R.string.opcion2) )
                {
                    ivMarcaSeleccionada.setImageResource(R.drawable.mazda)
                }
                if( tietMarca.text.toString() == resources.getText(R.string.opcion3) )
                {
                    ivMarcaSeleccionada.setImageResource(R.drawable.toyota)
                }
                if( tietMarca.text.toString() == resources.getText(R.string.opcion4) )
                {
                    ivMarcaSeleccionada.setImageResource(R.drawable.mercedes)
                }

            }
        }

        val makers = resources.getStringArray(R.array.listado_marcas)
        val adapter = ArrayAdapter(this@EditActivity, R.layout.list_makers , makers )

        with(binding.tietMarca){
            setAdapter(adapter)
            onItemClickListener = this@EditActivity
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@EditActivity, DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
        finish()
    }

    fun click (view: View)
    {
        with(binding)
        {
            if(!tietMarca.text.toString().isEmpty() && !tietModelo.text.toString().isEmpty() && !tietAnio.text.toString().isEmpty() && !tietPrecio.text.toString().isEmpty())
            {
                if( dbAutos.updateAuto( id , tietMarca.text.toString(), tietModelo.text.toString() , Integer.parseInt(tietAnio.text.toString()) , Integer.parseInt(tietPrecio.text.toString()) )){
                        Toast.makeText(this@EditActivity, getString(R.string.mensaje_actualizado), Toast.LENGTH_LONG).show()
                        val intent = Intent(this@EditActivity, DetailsActivity::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                        finish()
                } else {
                    Toast.makeText(this@EditActivity, getString(R.string.error_actualizar), Toast.LENGTH_LONG).show()
                    }
            } else{
                Toast.makeText(this@EditActivity, getString(R.string.llena_campos), Toast.LENGTH_LONG).show()
            }
        }
    }
}