
package com.adan.githubuserapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private lateinit var progressbar: ProgressBar

    private var title = "Github User App"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarTitle(title)

        progressbar = findViewById(R.id.progressbar)
        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        getUserFromApi()
    }

    private fun parseSuccessResponse(response: String, isSearch: Boolean) {
       try {
            val users = arrayListOf<User>()
            val result = when(isSearch){
                true -> JSONArray(response)
                false -> JSONObject(response).getJSONArray("items")
            }

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
            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
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
        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun getUserFromApi(username: String = "") {
        rvUser.visibility = View.GONE
        progressbar.visibility = View.VISIBLE

        val client = AsyncHttpClient()
        val isSearch = username.isBlank()
        val url = when(isSearch) {
            true  -> "https://api.github.com/users"
            false -> "https://api.github.com/search/users?q=$username"
        }
        client.addHeader("User-Agent", "Github-User-App")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                parseSuccessResponse(String(responseBody), isSearch)
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                showFailureResponse(statusCode, error)
            }
        })
    }

    private fun renderRecyclerList(list: ArrayList<User>) {
        val listUserAdapter = UserListAdapter(list)

        rvUser.layoutManager = LinearLayoutManager(this)
        rvUser.adapter = listUserAdapter

        listUserAdapter.onItemClick = { user ->
            val intent = Intent(this@MainActivity, UserDetails::class.java)
            intent.putExtra("avatar_url", user.avatar_url)
            intent.putExtra("username", user.username)
            intent.putExtra("usertype", user.usertype)
            startActivity(intent)

       }

        rvUser.visibility = View.VISIBLE
        progressbar.visibility = View.GONE

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu);

        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getUserFromApi(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setActionBarTitle(title: String) {
        (supportActionBar as ActionBar)?.title = title
    }
}