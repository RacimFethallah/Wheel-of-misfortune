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
             $WHEEL_REF INTEGER,
             FOREIGN KEY ($WHEEL_REF) REFERENCES $TABLE_NAME($COLUMN_ID) ON DELETE CASCADE
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

    fun addval(name: String, wheelid: Long?){
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

    fun getVal(wheelId: Long?): Cursor? {
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM $TABLE_VAL WHERE $WHEEL_REF = $wheelId", null)
    }

    fun deleteWheel(selectedItem: String){
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_VAL WHERE $WHEEL_REF IN (SELECT $COLUMN_ID FROM $TABLE_NAME WHERE $COLUMN_NAME = ?)", arrayOf(selectedItem))
        db.delete(TABLE_NAME, "$COLUMN_NAME = ?", arrayOf(selectedItem))
        db.close()
    }


    fun deleteVal(name: String){
        val db = this.writableDatabase
        db.delete(TABLE_VAL, "$VALUES_NAME = ?", arrayOf(name))
        db.close()
    }



    fun getWheelIdByName(name: String): Long? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID),
            "$COLUMN_NAME = ?",
            arrayOf(name),
            null,
            null,
            null
        )

        var wheelId: Long? = null
        if (cursor.moveToFirst()) {
            val index = cursor.getColumnIndex(COLUMN_ID)
            if (index != -1) {
                wheelId = cursor.getLong(index)
            }
        }
        cursor.close()
        return wheelId
    }


    fun getWheelId(wheelId: Long): Long? {
        val query = "SELECT $COLUMN_ID FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = readableDatabase.rawQuery(query, arrayOf(wheelId.toString()))

        val id: Long? = if (cursor.moveToFirst()) {
            cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
        } else {
            null
        }

        cursor.close()
        return id
    }


}