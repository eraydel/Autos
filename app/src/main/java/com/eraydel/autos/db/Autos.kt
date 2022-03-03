package com.eraydel.autos.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.eraydel.autos.model.Auto

class Autos(context: Context?) : DBHelper(context){

    val context = context

    /*
    * Method: insertAuto
    * Description: Insert a new car on catalog
    * Created by: Erick Ayala
    * Created at: 28-02-2022
     */
    fun insertAuto(maker: String, model: String, year: Int, price: Int): Long {

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var id: Long = 0

        try {
            val values = ContentValues()
            values.put("maker",maker)
            values.put("model",model)
            values.put("year",year)
            values.put("price",price)

            id = db.insert(TABLE_AUTOS , null, values )

        } catch (e: Exception){

        } finally {
            db.close()
        }

        return id
    }


    /*
    * Method: getAutos
    * Description: Get all autos from database
    * Created by: Erick Ayala
    * Created at: 28-02-2022
     */
    fun getAutos(): ArrayList<Auto>
    {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var listAutos = ArrayList<Auto>()
        var tmpAuto: Auto? = null
        var cursorAutos: Cursor? = null

        cursorAutos = db.rawQuery("SELECT * FROM $TABLE_AUTOS" , null)
        if(cursorAutos.moveToFirst()){
            do{
                tmpAuto =Auto(cursorAutos.getInt(0) , cursorAutos.getString(1) , cursorAutos.getString(2),cursorAutos.getInt(3),cursorAutos.getInt(4))
                listAutos.add(tmpAuto)
            } while(cursorAutos.moveToNext())
        }

        cursorAutos.close()

        return listAutos

    }


    /*
    * Method: getAuto
    * Description: Get an auto by id
    * Created by: Erick Ayala
    * Created at: 28-02-2022
     */
    fun getAuto(id: Int) : Auto? {
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var auto: Auto? = null
        var cursorAutos: Cursor? = null

        cursorAutos = db.rawQuery("SELECT * FROM $TABLE_AUTOS WHERE id = $id LIMIT 1" , null)

        if(cursorAutos.moveToFirst()){
            auto = Auto(cursorAutos.getInt(0) ,cursorAutos.getString(1), cursorAutos.getString(2), cursorAutos.getInt(3) , cursorAutos.getInt(4))
        }

        cursorAutos.close()

        return auto

    }

    /*
    * Method: deleteAuto
    * Description: Delete an auto by id
    * Created by: Erick Ayala
    * Created at: 28-02-2022
     */
    fun deleteAuto(id: Int): Boolean {
        var banderaCorrecto = false
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try {
            db.execSQL("DELETE FROM $TABLE_AUTOS WHERE id = $id ")
            banderaCorrecto = true

        } catch (e: Exception){

        } finally {
            db.close()
        }

        return banderaCorrecto
    }

    /*
    * Method: updateAuto
    * Description: Update an auto by id
    * Created by: Erick Ayala
    * Created at: 02-03-2022
     */
    fun updateAuto(id: Int , maker: String, model: String, year: Int, price: Int ) : Boolean
    {
        var banderaCorrecto = false
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try {
            db.execSQL("UPDATE $TABLE_AUTOS SET maker = '$maker' , model = '$model' , year = '$year' , price = '$price' WHERE id = $id ")
            banderaCorrecto = true

        }catch (e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto

    }
}