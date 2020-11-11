package com.tatvasoftpractical.views

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appideas.utils.layoutInflater
import com.tatvasoftpractical.databinding.ItemUserDetailsBinding
import com.tatvasoftpractical.model.UserDetailsModel


class UserListAdapter(
    private val context: Context,
    private val userList: ArrayList<UserDetailsModel>
) : RecyclerView.Adapter<UserListAdapter.Item>() {

    private lateinit var imagesAdapter: ImagesAdapter
    private lateinit var manager: GridLayoutManager

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Item = Item(ItemUserDetailsBinding.inflate(parent.context.layoutInflater()))

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserListAdapter.Item, position: Int) {

        holder.binding.tvUserName.text = userList[position].name
        Glide.with(context)
            .load(userList[position].image)
            .into(holder.binding.ivUserImage)

        var gridPerRow = 2

        manager = GridLayoutManager(context, gridPerRow)

        if ((userList[position].items!!.size % 2) != 0) {
            manager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 0) {
                        2
                    } else {
                        1
                    }
                }
            }
        }

        initImagesRecyclerView(holder, userList[position].items)
    }

    class Item(val binding: ItemUserDetailsBinding) : RecyclerView.ViewHolder(binding.root)

    private fun initImagesRecyclerView(holder: UserListAdapter.Item, items: ArrayList<String>?) {

        imagesAdapter = ImagesAdapter(context, items)
        holder.binding.rvImageList.layoutManager = manager
        holder.binding.rvImageList.adapter = imagesAdapter

    }

}