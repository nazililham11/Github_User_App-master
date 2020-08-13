package com.adan.githubuserapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.adan.githubuserapp.UserContract.UserColums

class UserHelper(var context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${UserColums.TABLE_NAME}" +
            " (${UserColums.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
            " ${UserColums.COLUMN_NAME_AVATAR_URL} TEXT," +
            " ${UserColums.COLUMN_NAME_USERNAME} TEXT," +
            " ${UserColums.COLUMN_NAME_USERTYPE} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${UserColums.TABLE_NAME}"

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Favourtes.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertData(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(UserColums.COLUMN_NAME_AVATAR_URL, user.avatar_url)
        values.put(UserColums.COLUMN_NAME_USERNAME, user.username)
        values.put(UserColums.COLUMN_NAME_USERTYPE, user.usertype)

        val result = db.insert(UserColums.TABLE_NAME, null, values)

        if (result == (0).toLong()) {
            Toast.makeText(context, "Add to Favourites Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Add to Favourites Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readUser(): ArrayList<User> {
        val list = arrayListOf<User>()
        val db = this.readableDatabase
        val query = "Select * from ${UserColums.TABLE_NAME}"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val user = User()
                user.avatar_url = result.getString(result.getColumnIndex(UserColums.COLUMN_NAME_AVATAR_URL))
                user.username = result.getString(result.getColumnIndex(UserColums.COLUMN_NAME_USERNAME))
                user.usertype = result.getString(result.getColumnIndex(UserColums.COLUMN_NAME_USERTYPE))
                user.isFavourite = true
                list.add(user)
            }
            while (result.moveToNext())
        }
        return list
    }

    fun deteleUser(user: User) {
        val db = this.writableDatabase
        val selection = UserColums.COLUMN_NAME_USERNAME + " = ?"
        val selectionArgs = arrayOf(user.username)
        db.delete(UserColums.TABLE_NAME, selection, selectionArgs)
    }

    fun isExist(user: User): Boolean {
        val db = this.readableDatabase
        val projection = arrayOf(UserColums.COLUMN_NAME_USERNAME)
        val selection = UserColums.COLUMN_NAME_USERNAME + " = ?"
        val selectionArgs = arrayOf(user.username)
        val result = db.query(UserColums.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
        return result.moveToFirst()
    }

}