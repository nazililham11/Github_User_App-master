package com.adan.githubuserapp

import android.net.Uri
import android.provider.BaseColumns

class FavouriteContract {

    companion object {
        val AUTHORITY = "com.adan.githubuserapp"
        val FAVOURITE_TABLE = "favourite"
        val CONTENT_URI : Uri = Uri.parse("content://" + AUTHORITY + "/" + FAVOURITE_TABLE)
    }

    class FavouriteDB : BaseColumns {
        companion object {
            val _ID = "_id"
            val AVATAR_URL = "avatar_url"
            val USERNAME = "username"
            val USERTYPE = "usertype"
        }
    }

}
