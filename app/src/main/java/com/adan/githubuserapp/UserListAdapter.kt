package com.adan.githubuserapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class
    UserListAdapter(private val listUser: ArrayList<User>)
    : RecyclerView.Adapter<UserListAdapter.ListViewHolder>()
    {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    : ListViewHolder
    {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }


    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.tvUserName.text = user.username
        holder.tvUserType.text = user.usertype

        Picasso.get().load(user.avatar_url).into(holder.imgAvatar)

        val mContext = holder.itemView.context

//        holder. itemView.setOnClickListener {
//            val intentDetail = Intent(mContext, DetailUser::class.java)
//            intentDetail.putExtra(DetailUser.EXTRA_AVATAR, avatar)
//            intentDetail.putExtra(DetailUser.EXTRA_USERNAME, username)
//
//            mContext.startActivity(intentDetail)
//            var bundle = Bundle()
//            bundle.putParcelable("selected_user", user)
//            intentDetail.putExtra("userBundle", bundle)
//            mContext.startActivity(intentDetail)
//            Toast.makeText(mContext, "Kamu Memilih $username", Toast.LENGTH_SHORT).show()
//        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        var tvUserName: TextView = itemView.findViewById(R.id.tv_username)
        var tvUserType: TextView = itemView.findViewById(R.id.tv_usertype)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}