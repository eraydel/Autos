package com.eraydel.autos.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DBHelper(context: Context?) : SQLiteOpenHelper(context , DATABASE_NAME , null , DATABASE_VERSION ){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE $TABLE_AUTOS (id INTEGER PRIMARY KEY AUTOINCREMENT, maker TEXT NOT NULL, model TEXT NOT NULL, year INTEGER NOT NULL , price INTEGER NOT NULL )")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE $TABLE_AUTOS")
        onCreate(p0)
    }

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "catalog.db"
        public const val TABLE_AUTOS = "autos"
    }
}