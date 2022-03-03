package com.eraydel.autos

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.eraydel.autos.databinding.ActivityDetailsBinding
import com.eraydel.autos.db.Autos
import com.eraydel.autos.model.Auto

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var dbAutos: Autos
    var auto: Auto? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null)
        {
            val bundle = intent.extras
            if(bundle != null)
            {
                id = bundle.getInt("id" , 0)
            }
        } else {
            id = savedInstanceState.getSerializable("id") as Int
        }


        dbAutos = Autos(this)
        auto = dbAutos.getAuto(id)
        if(auto != null)
        {
            with(binding)
            {
                supportActionBar?.title = getString( R.string.detalle_auto )
                tietMarca.setText(auto?.maker)
                tietModelo.setText(auto?.model)
                tietAnio.setText(Integer.parseInt(auto?.year.toString()).toString())
                tietPrecio.setText(Integer.parseInt(auto?.price.toString()).toString())

                if( tietMarca.text.toString() == resources.getText(R.string.opcion1) )
                {
                    ivMarcaDetail.setImageResource(R.drawable.tesla)
                }
                if( tietMarca.text.toString() == resources.getText(R.string.opcion2) )
                {
                    ivMarcaDetail.setImageResource(R.drawable.mazda)
                }
                if( tietMarca.text.toString() == resources.getText(R.string.opcion3) )
                {
                    ivMarcaDetail.setImageResource(R.drawable.toyota)
                }
                if( tietMarca.text.toString() == resources.getText(R.string.opcion4) )
                {
                    ivMarcaDetail.setImageResource(R.drawable.mercedes)
                }

                tietMarca.inputType = InputType.TYPE_NULL
                tietModelo.inputType = InputType.TYPE_NULL
                tietAnio.inputType = InputType.TYPE_NULL
                tietPrecio.inputType = InputType.TYPE_NULL
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    fun click(view: View) {
        when(view.id){
            R.id.btnEdit -> {
                val intent = Intent(this,EditActivity::class.java)
                intent.putExtra("id" , id)
                startActivity(intent)
                finish()
            }

            R.id.btnDelete -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.msjDialogTitulo))
                    .setMessage(getString(R.string.msjDialogPregunta,auto?.model))
                    .setPositiveButton(getString(R.string.mensajeDialogSi), DialogInterface.OnClickListener { dialogInterface, i ->
                        if(dbAutos.deleteAuto(id)){
                            Toast.makeText(this,getString(R.string.mensaje_eliminado), Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                    })
                    .setNegativeButton(getString(R.string.mensajeDialogNo), DialogInterface.OnClickListener { dialogInterface, i ->

                    })
                    .show()
                }

        }
    }

}