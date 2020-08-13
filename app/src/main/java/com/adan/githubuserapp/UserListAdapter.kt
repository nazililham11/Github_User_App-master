package com.adan.githubuserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class
    UserListAdapter(private val listUser: ArrayList<User>)
    : RecyclerView.Adapter<UserListAdapter.ListViewHolder>()
    {

    var onItemClick: ((User) -> Unit)? = null

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
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        var tvUserName: TextView = itemView.findViewById(R.id.tv_username)
        var tvUserType: TextView = itemView.findViewById(R.id.tv_usertype)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(listUser[adapterPosition])
            }
        }
    }
}