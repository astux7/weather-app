package com.example.astux7.weatheralert.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.astux7.weatheralert.model.*
import java.util.*

/**
 * Created by astux7 on 25/08/2017.
 */

class LocationDatabaseHandler(context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)"
        db?.execSQL(CREATE_LOCATION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun createLocation(city: String) {
        val db: SQLiteDatabase = writableDatabase
        val values :ContentValues = ContentValues()

        values.put(KEY_NAME, city)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun readLocations(): ArrayList<Location> {
        val db: SQLiteDatabase = readableDatabase
        val list: ArrayList<Location> = ArrayList()
        val selectAll = "SELECT * FROM " + TABLE_NAME
        val cursor: Cursor = db.rawQuery(selectAll, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                list.add(Location(id, name))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun deleteLocation(id: Int) {
        val db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))
        db.close()
    }

    fun getLocationCount(): Int {
        val db: SQLiteDatabase = readableDatabase
        val countQuery: String = "SELECT * FROM " + TABLE_NAME
        val cursor: Cursor = db.rawQuery(countQuery, null)
        val listCount = cursor.count
        cursor.close()
        return listCount
    }
}