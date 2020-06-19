package com.adan.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.item_row_user.*

class DetailUser : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_NAMAUSER = "extra_namauser"
        const val EXTRA_DATACOMPANY = "extra_datacompany"
        const val EXTRA_DATALOCATION = "extra_datalocation"
        const val EXTRA_DATAREPOSITORY = "extra_datarepository"
        const val EXTRA_DATAFOLLOWER = "extra_datafollower"
        const val EXTRA_DATAFOLLOWING = "extra_datafollowing"
    }

//    private lateinit var imgAvatar: ImageView
//    private lateinit var tvUserName: TextView
//    private lateinit var tvNamaUser: TextView
//    private lateinit var tvDataCompany: TextView
//    private lateinit var tvDataLocation: TextView
//    private lateinit var tvDataRepository: TextView
//    private lateinit var tvDataFollower: TextView
//    private lateinit var tvDataFollowing: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val actionBar = supportActionBar
        this.title = "Detail User"
        actionBar.run {
            this!!.title = "Detail User"
            setDisplayHomeAsUpEnabled(true)
        }

//        imgAvatar = findViewById(R.id.dtl_avatar)
//        tvUserName = findViewById(R.id.dtl_username)
//        tvNamaUser = findViewById(R.id.dtl_namauser)
//        tvDataCompany = findViewById(R.id.dtl_data_company)
//        tvDataLocation = findViewById(R.id.dtl_data_location)
//        tvDataRepository = findViewById(R.id.dtl_data_repository)
//        tvDataFollower = findViewById(R.id.dtl_data_follower)
//        tvDataFollowing = findViewById(R.id.dtl_data_following)

        val bundle = intent.getBundleExtra("userBundle")
        var user  = bundle.getParcelable<User>("selected_user") as User





//        val avatar = intent.getParcelableExtra(EXTRA_AVATAR) as User
//        val username = intent.getParcelableExtra(EXTRA_USERNAME) as User
//        val namauser = intent.getParcelableExtra(EXTRA_NAMAUSER) as User
//        val datacompany = intent.getParcelableExtra(EXTRA_DATACOMPANY) as User
//        val datalocation = intent.getParcelableExtra(EXTRA_DATALOCATION) as User
//        val datarepository = intent.getParcelableExtra(EXTRA_DATAREPOSITORY) as User
//        val datafollower = intent.getParcelableExtra(EXTRA_DATAFOLLOWER) as User
//        val datafollowing = intent.getParcelableExtra(EXTRA_DATAFOLLOWING) as User
//
        if (user is User){
            Log.d("in if","user is User")
//            dtl_username.text = user.username
//            dtl_namauser.text = user.namauser
//            dtl_data_company.text = user.datacompany
//            dtl_data_location.text = user.datalocation
//            dtl_data_repository.text = user.datarepository.toString()
//            dtl_data_follower.text = user.datafollower.toString()
//            dtl_data_following.text = user.datafollowing.toString()
//
//            Glide.with(applicationContext)
//                .load(user.avatar)
//                .apply(RequestOptions().override(500, 500))
//                .into(dtl_avatar)
        } else {
            Log.d("CC","use isnt User")
            Log.d("DD", user.toString())
        }
//
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}