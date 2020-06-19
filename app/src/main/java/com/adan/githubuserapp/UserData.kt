package com.adan.githubuserapp

object UserData{
    private val userAvatar = arrayOf(R.drawable.user1,
        R.drawable.user2,
        R.drawable.user3,
        R.drawable.user4,
        R.drawable.user5,
        R.drawable.user6,
        R.drawable.user7,
        R.drawable.user8,
        R.drawable.user9,
        R.drawable.user10)

    private val userName = arrayOf("JakeWharton",
        "amitshekhariitbhu",
        "romainguy",
        "chrisbanes",
        "tipsy",
        "ravi8x",
        "jasoet",
        "budioktaviyan",
        "hendisantika",
        "sidiqpermana")

    private val namaUser = arrayOf("Jake Wharton",
        "AMIT SHEKHAR",
        "Romain Guy",
        "Chris Banes",
        "David",
        "Ravi Tamada",
        "Deny Prasetyo",
        "Budi Oktaviyan",
        "Hendi Santika",
        "Sidiq Permana")

    private val dataCompany = arrayOf("Google, Inc.",
        "@MindOrksOpenSource",
        "Google",
        "@google working on @android",
        "Working Group Two",
        "AndroidHive | Droid5",
        "@gojek-engineering",
        "@KotlinID",
        "@JVMDeveloperID @KotlinID @IDDevOps",
        "Nusantara Beta Studio" )

    private val dataLocation = arrayOf("Pittsburgh, PA, USA",
        "New Delhi, India",
        "California",
        "Sydney, Australia",
        "Trondheim, Norway",
        "India",
        "Kotagede, Yogyakarta, Indonesia",
        "Jakarta, Indonesia",
        "Bojongsoang - Bandung Jawa Barat",
        "Jakarta Indonesia")

    private val dataRepository = arrayOf(102,
        37,
        9,
        30,
        56,
        28,
        44,
        110,
        1064,
        65)

    private val dataFollower = arrayOf(56995,
        5153,
        7972,
        14725,
        788,
        18628,
        277,
        178,
        428,
        565)

    private val dataFollowing = arrayOf(12,
        2,
        0,
        1,
        0,
        3,
        39,
        23,
        61,
        10)

    val listData: ArrayList<User>
        get() {
            val list = arrayListOf<User>()
            for (position in userAvatar.indices) {
                val user = User()
                user.avatar = userAvatar[position]
                user.username = userName[position]
                user.namauser = namaUser[position]
                user.datacompany = dataCompany[position]
                user.datalocation = dataLocation[position]
                user.datarepository = dataRepository[position]
                user.datafollower = dataFollower[position]
                user.datafollowing = dataFollowing[position]
                list.add(user)
            }
            return list
        }
}










