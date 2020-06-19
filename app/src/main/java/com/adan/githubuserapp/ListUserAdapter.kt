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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.view.*

class ListUserAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListUserAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        var tvUserName: TextView = itemView.findViewById(R.id.tv_username)
        var tvNamaUser: TextView = itemView.findViewById(R.id.tv_nama)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (avatar, username, namauser, datacompany, datalocation, datarepository, datafollower, datafollowing) = listUser[position]
        val user = listUser[position]
        Glide.with(holder.itemView.context)
            .load(avatar)
            .apply(RequestOptions())
            .into(holder.imgAvatar)

        holder.tvUserName.text = username
        holder.tvNamaUser.text = namauser

        val mContext = holder.itemView.context
        holder. itemView.setOnClickListener {
            val intentDetail = Intent(mContext, DetailUser::class.java)
            intentDetail.putExtra(DetailUser.EXTRA_AVATAR, avatar)
            intentDetail.putExtra(DetailUser.EXTRA_USERNAME, username)
            intentDetail.putExtra(DetailUser.EXTRA_NAMAUSER, namauser)
            intentDetail.putExtra(DetailUser.EXTRA_DATACOMPANY, datacompany)
            intentDetail.putExtra(DetailUser.EXTRA_DATALOCATION, datalocation)
            intentDetail.putExtra(DetailUser.EXTRA_DATAREPOSITORY, datarepository)
            intentDetail.putExtra(DetailUser.EXTRA_DATAFOLLOWER, datafollower)
            intentDetail.putExtra(DetailUser.EXTRA_DATAFOLLOWING, datafollowing)
            mContext.startActivity(intentDetail)
            var bundle = Bundle()
            bundle.putParcelable("selected_user", user)
            intentDetail.putExtra("userBundle", bundle)
            mContext.startActivity(intentDetail)
            Toast.makeText(mContext, "Kamu Memilih " + username, Toast.LENGTH_SHORT).show()
        }
    }


    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}