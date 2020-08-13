package com.adan.githubuserapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var avatar_url: String = "",
    var username: String = "",
    var usertype: String = "",
    var isFavourite: Boolean = false
) : Parcelable