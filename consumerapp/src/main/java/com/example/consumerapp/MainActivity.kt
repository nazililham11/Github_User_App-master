package com.example.consumerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        setActionBarTitle("Favourites User")

        getFavouritesUser()
    }

    override fun onResume() {
        super.onResume()
        getFavouritesUser()
    }

    private fun getFavouritesUser(){
        rvUser.visibility = View.GONE
        val users = arrayListOf<User>()
        val cols = arrayOf(FavouritesProvider.COLUMN_AVATAR_URL, FavouritesProvider.COLUMN_USERNAME, FavouritesProvider.COLUMN_USERTYPE)
        val u = FavouritesProvider.CONTENT_URI
        val c = contentResolver.query(u, cols, null, null, null)

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    val user = User()
                    user.avatar_url = c.getString(c.getColumnIndex(FavouritesProvider.COLUMN_AVATAR_URL))
                    user.username = c.getString(c.getColumnIndex(FavouritesProvider.COLUMN_USERNAME))
                    user.usertype = c.getString(c.getColumnIndex(FavouritesProvider.COLUMN_USERTYPE))
                    user.isFavourite = true
                    users.add(user)
                }
                while (c.moveToNext())
            }
        }

        renderRecyclerList(users)
//        var db = UserHelper(this@FavouritesUser)
//        var users = db.readUser()

//        renderRecyclerList(users)

    }

    private fun renderRecyclerList(list: ArrayList<User>) {
        val listUserAdapter = UserListAdapter(list)

        rvUser.layoutManager = LinearLayoutManager(this)
        rvUser.adapter = listUserAdapter

        rvUser.visibility = View.VISIBLE
    }

    private fun setActionBarTitle(title: String) {
        (supportActionBar as ActionBar)?.title = title
    }

}
