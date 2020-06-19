package com.adan.githubuserapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var avatar: Int = 0,
    var username: String = "",
    var namauser: String = "",
    var datacompany: String = "",
    var datalocation: String = "",
    var datarepository: Int = 0,
    var datafollower: Int = 0,
    var datafollowing: Int = 0
) : Parcelable