package com.example.astux7.weatheralert.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.astux7.weatheralert.model.*

/**
 * Created by astux7 on 25/08/2017.
 */
class ForecastDatabaseHandler(context: Context):
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                                    KEY_NAME + " TEXT)"
        db?.execSQL(CREATE_LOCATION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun createLocation(location: Location) {
        var db: SQLiteDatabase = writableDatabase
        var values :ContentValues = ContentValues()

        values.put(KEY_NAME, location.name)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun readLocations(): ArrayList<Location> {
        var db: SQLiteDatabase = readableDatabase
        var list: ArrayList<Location> = ArrayList()
        var selectAll = "SELECT * FROM " + TABLE_NAME
        var cursor: Cursor = db.rawQuery(selectAll, null)

        if(cursor.moveToFirst()) {
            do {
                var location = Location()
                location.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                location.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                list.add(location)
            } while (cursor.moveToNext())
        }
        return list
    }

    fun deleteLocation(id: Int) {
        var db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))
        db.close()
    }

    fun getLocationCount(): Int {
        var db: SQLiteDatabase = readableDatabase
        var countQuery: String = "SELECT * FROM " + TABLE_NAME
        var cursor: Cursor = db.rawQuery(countQuery, null)

        return cursor.count
    }

}