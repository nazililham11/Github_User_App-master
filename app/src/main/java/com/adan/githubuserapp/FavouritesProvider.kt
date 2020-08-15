package com.adan.githubuserapp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.provider.BaseColumns

class FavouritesProvider : ContentProvider() {

    companion object {
        val AUTHORITY = "com.adan.githubuserapp"
        val TABLE_NAME = "favourites"
        val CONTENT_URI : Uri = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME)

        val _ID = "_id"
        val AVATAR_URL = "avatar_url"
        val USERNAME = "username"
        val USERTYPE = "usertype"
    }

    private var dbHelper: DBHelper? = null
    private val DATABASE_NAME = "table.db"
    private val DATABASE_VERSION = 1

    private var sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private val DATUM = 1
    private val DATUM_ID = 2
    private var projMap = mutableMapOf<String, String>()

    init {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, DATUM)
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", DATUM_ID)
        projMap.put(Constants.ID, Constants.ID)
        projMap.put(Constants.TEXT, Constants.TEXT)
    }

    override fun insert(uri: Uri?, cv: ContentValues?): Uri? {
        if (sUriMatcher.match(uri) != DATUM)
            throw IllegalArgumentException("Unknown URI " + uri)
        val v: ContentValues
        if (cv != null)
            v = ContentValues(cv)
        else
            v = ContentValues()

        val db = dbHelper?.writableDatabase
        val rId = db?.insert(TABLE_NAME, Constants.TEXT, v)
        if (rId != null) {
            if (rId > 0) {
                val uri = ContentUris.withAppendedId(Constants.URL, rId)
                context.contentResolver.notifyChange(uri, null)
                return uri
            }
        } else {
            throw SQLException("Failed to insert row into " + uri)
        }
        return null
    }

    override fun query(uri: Uri?, p: Array<out String>?, s: String?, args: Array<out String>?, sort: String?): Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = DATUM_TABLE_NAME
        qb.setProjectionMap(projMap)
        var s1 = s
        if (sUriMatcher.match(uri) != DATUM) {
            if (sUriMatcher.match(uri) == DATUM_ID)
                s1 = s + "_id = " + uri?.lastPathSegment
            else
                throw IllegalArgumentException("Unknown URI " + uri)
        }
        val db = dbHelper?.readableDatabase
        val c = qb.query(db, p, s1, args, null, null, sort)
        c.setNotificationUri(context.contentResolver, uri)
        return c
    }

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
        return true
    }

    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 1
    }

    override fun delete(p0: Uri?, p1: String?, p2: Array<out String>?): Int {
        return 1
    }

    override fun getType(uri: Uri?): String {
        if (sUriMatcher.match(uri) == DATUM)
            return Constants.CONTENT_TYPE
        else
            throw IllegalArgumentException("Unknown URI " + uri)
    }

    inner class DBHelper(context: Context?,
                         name: String?,
                         factory: SQLiteDatabase.CursorFactory?,
                         version: Int) : SQLiteOpenHelper(context, name, factory, version) {

        private val SQL_CREATE_ENTRIES = "CREATE TABLE ${TABLE_NAME}" +
                " (${_ID} INTEGER PRIMARY KEY," +
                " ${AVATAR_URL} TEXT," +
                " ${USERNAME} TEXT," +
                " ${USERTYPE} TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"

        override fun onCreate(_db: SQLiteDatabase?) {
            _db?.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(_db: SQLiteDatabase?, _oldVersion: Int, _newVersion: Int) {
            _db?.execSQL(SQL_DELETE_ENTRIES)
            onCreate(_db)
        }
    }
}