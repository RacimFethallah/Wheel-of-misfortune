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
        const val TABLE_VAL = "value"
        const val VALUES_ID = "id"
        const val VALUES_NAME = "name"
        const val WHEEL_REF = "refw"
    }


    override fun onCreate(db: SQLiteDatabase) {
        val createTableWheels = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT)"
        val createTableValues = """ 
             CREATE TABLE $TABLE_VAL (
             $VALUES_ID INTEGER PRIMARY KEY AUTOINCREMENT,
             $VALUES_NAME TEXT,
             $WHEEL_REF REFERENCES $TABLE_NAME ($COLUMN_ID)
             )
        """.trimIndent()
        db.execSQL(createTableWheels)
        db.execSQL(createTableValues)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_VAL")
        onCreate(db)
    }


    fun addWheeldb(name : String){

        val values = ContentValues()

        values.put(COLUMN_NAME, name)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun addval(name: String, wheelid: Int){
        val values = ContentValues()

        values.put(VALUES_NAME, name)
        values.put(WHEEL_REF, wheelid)

        val db = this.writableDatabase

        db.insert(TABLE_VAL, null, values)

        db.close()
    }


    fun getNamedb(): Cursor? {


        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun getVal(): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM $TABLE_VAL, $TABLE_NAME WHERE $TABLE_VAL.$WHEEL_REF = $TABLE_NAME.$COLUMN_ID", null)
    }

    fun deleteWheel(selectedItem: String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_NAME = ?", arrayOf(selectedItem))
        db.close()
    }


    fun deleteVal(name: String){
        val db = this.writableDatabase
        db.delete(TABLE_VAL, "$VALUES_NAME = ?", arrayOf(name))
        db.close()
    }


}