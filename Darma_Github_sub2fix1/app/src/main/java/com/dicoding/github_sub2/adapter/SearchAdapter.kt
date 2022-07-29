package com.dicoding.github_sub2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.github_sub2.databinding.ItemRowUserBinding
import com.dicoding.github_sub2.models.User

class SearchAdapter(private val listUsers: MutableList<User>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setUser(items: List<User>) {
        listUsers.clear()
        listUsers.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class SearchViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val userData = listUsers[position]
        Glide.with(holder.itemView.context)
            .load(userData.avatarUrl)
            .into(holder.binding.imgItemAvatar)
        holder.binding.tvItemUsername.text = userData.login
        holder.binding.tvItemCompany.text = userData.organizationsUrl

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(userData.login) }
    }

    override fun getItemCount(): Int = listUsers.size

    interface OnItemClickCallback {
        fun onItemClicked(user: String)
    }
}