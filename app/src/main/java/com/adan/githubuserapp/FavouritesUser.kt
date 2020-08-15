package com.adan.githubuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouritesUser : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites_user)

        progressBar = findViewById(R.id.progressbar)
        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        setActionBarTitle("Favourites")

        getFavouritesUser()
    }

    override fun onResume() {
        super.onResume()
        getFavouritesUser()
    }

    private fun getFavouritesUser(){
        rvUser.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

//        var db = UserHelper(this@FavouritesUser)
//        var users = db.readUser()

//        renderRecyclerList(users)

    }

    private fun renderRecyclerList(list: ArrayList<User>) {
        val listUserAdapter = UserListAdapter(list)

        rvUser.layoutManager = LinearLayoutManager(this)
        rvUser.adapter = listUserAdapter

        listUserAdapter.onItemClick = { user ->
            val intent = Intent(this@FavouritesUser, UserDetails::class.java)
            intent.putExtra("avatar_url", user.avatar_url)
            intent.putExtra("username", user.username)
            intent.putExtra("usertype", user.usertype)
            startActivity(intent)

        }

        rvUser.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun setActionBarTitle(title: String) {
        (supportActionBar as ActionBar)?.title = title
    }

}