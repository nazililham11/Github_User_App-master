package com.adan.githubuserapp

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils
import android.util.Log

class FavouritesUserProvider : ContentProvider() {



    private var myDB: FavouritesDbHandler? = null

    private val FAVOURITES = 1
    private val FAVOURITES_ID = 2

    private val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        sURIMatcher.addURI(FavouriteContract.AUTHORITY, FavouriteContract.FAVOURITE_TABLE, FAVOURITES)
        sURIMatcher.addURI(FavouriteContract.AUTHORITY, FavouriteContract.FAVOURITE_TABLE + "/#", FAVOURITES_ID)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val uriType = sURIMatcher.match(uri)
        val sqlDB = myDB!!.writableDatabase
        val rowsDeleted: Int

        when (uriType) {
            FAVOURITES -> rowsDeleted = sqlDB.delete(FavouriteContract.FAVOURITE_TABLE,
                selection,
                selectionArgs)

            FAVOURITES_ID -> {
                val username = uri.lastPathSegment
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(FavouriteContract.FAVOURITE_TABLE,
                        FavouriteContract.FavouriteDB.USERNAME + "=" + username,
                        null)
                } else {
                    rowsDeleted = sqlDB.delete(FavouriteContract.FAVOURITE_TABLE,
                        FavouriteContract.FavouriteDB.USERNAME + "=" + username
                                + " and " + selection,
                        selectionArgs)
                }
            }
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }
        context?.contentResolver?.notifyChange(uri, null)
        return rowsDeleted
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("FavouriteProvider", "Insert Start")
        val uriType = sURIMatcher.match(uri)
        val sqlDB = myDB!!.writableDatabase
        val id: Long

        when (uriType) {
            FAVOURITES -> id = sqlDB.insert(FavouriteContract.FAVOURITE_TABLE, null, values)
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }

        context?.contentResolver?.notifyChange(uri, null)
        Log.d("FavouriteProvider", "Insert Done")

        return Uri.parse(FavouriteContract.FAVOURITE_TABLE + "/" + id)
    }

    override fun onCreate(): Boolean {
        Log.d("FavProvider", "On Create")
        myDB = context?.let { FavouritesDbHandler(it) }
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = FavouriteContract.FAVOURITE_TABLE
        val uriType = sURIMatcher.match(uri)

        when (uriType) {
            FAVOURITES_ID -> queryBuilder.appendWhere(FavouriteContract.FavouriteDB.USERNAME + "=" + uri.lastPathSegment)
            FAVOURITES -> {}
            else -> throw IllegalArgumentException("Unknown URI")
        }

        val cursor = queryBuilder.query(myDB?.readableDatabase,
            projection, selection, selectionArgs, null, null,
            sortOrder)
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor

    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
