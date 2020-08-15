package com.example.consumerapp

import android.app.Application
import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.provider.BaseColumns
import android.util.Log


class FavouritesProvider : ContentProvider() {

    var _context: Context? = null

    companion object {

        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Favourtes.db"


        val TABLE_NAME = "favourites"
        val COLUMN_ID = "_id"
        val COLUMN_AVATAR_URL = "avatar_url"
        val COLUMN_USERNAME = "username"
        val COLUMN_USERTYPE = "usertype"

        private val AUTHORITY = "com.adan.githubuserapp"
        private val BASE_PATH = "favourites"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/$BASE_PATH")
        private val FAV = 1
        private val FAV_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, BASE_PATH, FAV)
            uriMatcher.addURI(AUTHORITY, "$BASE_PATH/#", FAV_ID)
        }
    }



    override fun onCreate(): Boolean {
        Log.d("FProv", "On Create")
        Log.d("FProv", "On Create Done")
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("FProv", "Insert")
        val dbHelper = DatabaseHelper(context?:_context)
        val db = dbHelper.writableDatabase
        val id = db!!.insert(TABLE_NAME, null, values)

        if (id != null) {
            if (id > 0) {
                Log.d("FProv", "Insert DOne")
                return ContentUris.withAppendedId(CONTENT_URI, id)
            }
        }
        Log.d("FProv", "Insert Done")
        throw SQLException("Insertion Failed for URI :$uri")
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        Log.d("FProv", "Query")
        val dbHelper = DatabaseHelper(context?:_context)
        val db = dbHelper.readableDatabase
        var cursor: Cursor? = null

        if (uriMatcher.match(uri) == FAV) {
            cursor = db!!.query(TABLE_NAME, null, selection, selectionArgs, null, null, null)
        }

        Log.d("FProv", "Query Done")
        return cursor
    }



    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>? ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d("FProv", "Query")
        val dbHelper = DatabaseHelper(context?:_context)
        val db = dbHelper.writableDatabase
        var delCount = 0
        when (uriMatcher.match(uri)) {
            FAV -> delCount = db.delete(TABLE_NAME, selection, selectionArgs)?:0
            else -> throw IllegalArgumentException("This is an Unknown URI $uri")
        }
        return delCount
    }

    override fun getType(uri: Uri): String? {
        return null
    }



    inner class DatabaseHelper(context: Context?) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {



        private val SQL_CREATE_ENTRIES = "CREATE TABLE ${TABLE_NAME}" +
                " (${COLUMN_ID} INTEGER PRIMARY KEY," +
                " ${COLUMN_AVATAR_URL} TEXT," +
                " ${COLUMN_USERNAME} TEXT," +
                " ${COLUMN_USERTYPE} TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"

        override fun onCreate(db: SQLiteDatabase?) {
            Log.d("DbHelper", "Create DB")
            db!!.execSQL(SQL_CREATE_ENTRIES)
            Log.d("DbHelper", "Create DB Done")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            Log.d("DbHelper", "Upgrade DB")

            db!!.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)

            Log.d("DbHelper", "Upgrade DB Done")
        }


    }

}