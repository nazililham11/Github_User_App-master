package com.adan.githubuserapp

import android.provider.BaseColumns

class UserContract {
    class UserColums : BaseColumns {
        companion object {
            val TABLE_NAME = "user"
            val COLUMN_NAME_ID = "_id"
            val COLUMN_NAME_AVATAR_URL = "avatar_url"
            val COLUMN_NAME_USERNAME = "username"
            val COLUMN_NAME_USERTYPE = "genre"
        }
    }
}