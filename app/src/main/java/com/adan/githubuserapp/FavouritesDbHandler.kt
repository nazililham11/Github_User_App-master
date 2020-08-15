package com.adan.githubuserapp

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.adan.githubuserapp.FavouriteContract.FavouriteDB

class FavouritesDbHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Favourtes.db"
    }

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${FavouriteContract.FAVOURITE_TABLE}" +
            " (${FavouriteDB._ID} INTEGER PRIMARY KEY," +
            " ${FavouriteDB.AVATAR_URL} TEXT," +
            " ${FavouriteDB.USERNAME} TEXT," +
            " ${FavouriteDB.USERTYPE} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FavouriteContract.FAVOURITE_TABLE}"


    private val myCR: ContentResolver

    init {
        myCR = context.contentResolver
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
        Log.d("DbHandler", "Create DB")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }


    fun addFavourite(user: User) {
        Log.d("DBHandler", "Insert Start")
        val values = ContentValues()
        values.put(FavouriteDB.AVATAR_URL, user.avatar_url)
        values.put(FavouriteDB.USERNAME, user.username)
        values.put(FavouriteDB.USERTYPE, user.usertype)

        myCR.insert(FavouriteContract.CONTENT_URI, values)
        Log.d("DBHandler", "Insert Done")
    }

    fun findFavourite(username: String): User? {
        val projection = arrayOf(FavouriteDB.AVATAR_URL, FavouriteDB.USERNAME, FavouriteDB.USERTYPE)

        val selection = "username = \"" + username + "\""

        val cursor = myCR.query(FavouriteContract.CONTENT_URI,
            projection, selection, null, null)

        var user: User? = null

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                cursor.moveToFirst()
                val _avatar = cursor.getString(cursor.getColumnIndex(FavouriteDB.AVATAR_URL))
                val _username = cursor.getString(cursor.getColumnIndex(FavouriteDB.USERNAME))
                val _usertype = cursor.getString(cursor.getColumnIndex(FavouriteDB.USERTYPE))

                user = User(
                    avatar_url = _avatar,
                    username = _username,
                    usertype = _usertype
                )
                cursor.close()
            }
        }
        return user
    }

    fun deleteFavourite(username: String): Boolean {
        var result = false
        val selection = "username = \"" + username + "\""
        val rowsDeleted = myCR.delete(FavouriteContract.CONTENT_URI, selection, null)
        if (rowsDeleted > 0)
            result = true
        return result
    }

}

