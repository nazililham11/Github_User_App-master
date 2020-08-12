package com.adan.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private var title = "Github User App"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBarTitle(title)

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)

        getUserFromApi()
    }

    private fun parseSuccessResponse(response: String, isSearch: Boolean) {
        Log.d("Success", "Request Success")

        Log.d("Response", response)

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
        Log.d("Fail", "Request Failure")

        val errorMessage = when (statusCode) {
            401 -> "$statusCode : Bad Request"
            403 -> "$statusCode : Forbidden"
            404 -> "$statusCode : Not Found"
            else -> "$statusCode : ${error.message}"
        }
        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun getUserFromApi(username: String = "") {
        Log.d("Start", "Start request")

        val client = AsyncHttpClient()
        val isSearch = username.isBlank()
        val url = when(isSearch) {
            true  -> "https://api.github.com/users"
            false -> "https://api.github.com/search/users?q=$username"
        }
        Log.d("Url", url)

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

//        listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: User) {
//                showSelectedUser(data)
//            }
//        })
    }

    private fun setActionBarTitle(title: String) {
        (supportActionBar as ActionBar)?.title = title
    }
}