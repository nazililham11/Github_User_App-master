package com.adan.githubuserapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_follower.*
import org.json.JSONArray

private const val ARG_PARAM1 = "username"

class FollowerFragment : Fragment() {
    private var username: String? = null
    private lateinit var rvFollower: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        var rootView = inflater.inflate(R.layout.fragment_follower, container, false)
        rvFollower =  rootView.findViewById(R.id.rv_follower)
        progressBar = rootView.findViewById(R.id.progressbar)
        renderToView()
        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }

    private fun renderToView(){
        progressBar.visibility = View.VISIBLE
        rvFollower.visibility = View.GONE

        val url = "https://api.github.com/users/${username}/followers"
        getUsersFromApi(url)
    }

    private fun parseSuccessResponse(response: String) {
        try {
            val users = arrayListOf<User>()
            val result = JSONArray(response)

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
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
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
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
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
        rvFollower.layoutManager = LinearLayoutManager(context)
        rvFollower.adapter = listUserAdapter

        progressBar.visibility = View.GONE
        rvFollower.visibility = View.VISIBLE
    }
}