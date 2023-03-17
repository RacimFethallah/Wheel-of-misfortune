package com.example.wheelofmisfortune

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Wheeldb (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "wheel_database"
        const val TABLE_NAME = "wheels"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }


    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


    fun addWheeldb(name : String){

        val values = ContentValues()

        values.put(COLUMN_NAME, name)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)

        db.close()
    }


    fun getNamedb(): Cursor? {


        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun deleteWheel(selectedItem: String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_NAME = ?", arrayOf(selectedItem))
        db.close()
    }


}