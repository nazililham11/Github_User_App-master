package com.adan.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.squareup.picasso.Picasso
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail_user.*
import org.json.JSONArray

class UserDetails : AppCompatActivity() {

    private lateinit var rvFollower: RecyclerView
    private lateinit var imgAvatar: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserType: TextView
    private lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        tvUserName = findViewById(R.id.tv_username)
        tvUserType = findViewById(R.id.tv_usertype)
        imgAvatar = findViewById(R.id.img_avatar)
        progressbar = findViewById(R.id.progressbar)

        rvFollower = findViewById(R.id.rv_follower)
        rvFollower.setHasFixedSize(true)

        val user = User(
            avatar_url = intent.getStringExtra("avatar_url"),
            username = intent.getStringExtra("username"),
            usertype = intent.getStringExtra("usertype")
        )

        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        viewPagerAdapter.username = user.username
        view_pager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f

        renderToView(user)
    }

    private fun renderToView(user: User){
        setActionBarTitle(user.username)

        progressbar.visibility = View.GONE
        rvFollower .visibility = View.GONE

        tvUserType.text = user.usertype
        tvUserName.text = user.username
        Picasso.get().load(user.avatar_url).into(imgAvatar)

        //Load Followers
//        val url = "https://api.github.com/users/${user.username}/followers"
//        getUsersFromApi(url)
    }

    private fun parseSuccessResponse(response: String) {
        try {
            val users = arrayListOf<User>()
            val result =JSONArray(response)

            for (i in 0 until result.length()){
                val obj = result.getJSONObject(i)
                val user = User(
                    avatar_url = obj.getString("avatar_url"),
                    username = obj.getString("login"),
                    usertype = obj.getString("type")
                )
                users.add(user)
            }

            renderRecyclerList(users)

        } catch (e: Exception) {
            Toast.makeText(this@UserDetails, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun showFailureResponse(statusCode: Int, error: Throwable) {
        val errorMessage = when (statusCode) {
            401 -> "$statusCode : Bad Request"
            403 -> "$statusCode : Forbidden"
            404 -> "$statusCode : Not Found"
            else -> "$statusCode : ${error.message}"
        }
        Toast.makeText(this@UserDetails, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun getUsersFromApi(url: String) {
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "Github-User-App")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                parseSuccessResponse(String(responseBody))
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                showFailureResponse(statusCode, error)
            }
        })
    }

    private fun renderRecyclerList(list: ArrayList<User>) {
        val listUserAdapter = UserListAdapter(list)
        rvFollower.layoutManager = LinearLayoutManager(this)
        rvFollower.adapter = listUserAdapter

        progressbar.visibility = View.GONE
        rvFollower.visibility = View.VISIBLE
    }

    private fun setActionBarTitle(title: String) {
        (supportActionBar as ActionBar)?.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}