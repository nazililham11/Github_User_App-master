package com.adan.githubuserapp

import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_user.*


class UserDetails : AppCompatActivity() {

    private lateinit var imgAvatar: ImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserType: TextView
    private lateinit var btnFavourite: ToggleButton
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        tvUserName = findViewById(R.id.tv_username)
        tvUserType = findViewById(R.id.tv_usertype)
        imgAvatar = findViewById(R.id.img_avatar)
        btnFavourite = findViewById(R.id.btn_favourite)


        user = User(
            avatar_url = intent.getStringExtra("avatar_url"),
            username = intent.getStringExtra("username"),
            usertype = intent.getStringExtra("usertype")
        )
        getIsFavourites()

        initViewPager()
        renderToView(user)
    }

    private fun getIsFavourites(){
//        val db = FavouritesDbHandler(this@UserDetails, "", )
//        val isFavourte = db.isExist(user)
//        this.user.isFavourite = isFavourte
//        this.setFavourites(isFavourte)
    }

    private fun initViewPager(){
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        viewPagerAdapter.username = user.username
        view_pager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    private fun setFavourites(isFavourite: Boolean){
        btn_favourite.isChecked = isFavourite
        btn_favourite.setBackgroundDrawable(
            if (isFavourite) ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_favorite_24)
            else ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_favorite_border_24)
        )
    }

    private fun writeFavourites(isFavourite: Boolean){
        val db = FavouritesDbHandler(this@UserDetails, "", null, 0)
        if (isFavourite) {
            db.addFavourite(user)
            Log.d("UserDetails", "Insert Start")

        }
//        val db = FavouritesUserProvider()
//        if (isFavourite)
//                db.insert()
//        var db = UserHelper(this@UserDetails)
//        if (isFavourite)
//            db.insertData(user)
//        else
//            db.deteleUser(user)
    }

    private fun renderToView(user: User){
        setActionBarTitle(user.username)
        tvUserType.text = user.usertype
        tvUserName.text = user.username
        Picasso.get().load(user.avatar_url).into(imgAvatar)

        setFavourites(user.isFavourite)

        btn_favourite.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            this.user.isFavourite = isChecked
            setFavourites(isChecked)
            writeFavourites(isChecked)
        })
    }

    private fun setActionBarTitle(title: String) {
        (supportActionBar as ActionBar)?.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}