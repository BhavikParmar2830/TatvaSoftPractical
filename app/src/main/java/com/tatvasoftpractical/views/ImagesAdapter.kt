package com.tatvasoftpractical.views

import android.R.attr.*
import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appideas.utils.layoutInflater
import com.tatvasoftpractical.databinding.ItemImagesBinding


class ImagesAdapter(
    private val context: Context,
    private val imageList: ArrayList<String>?
): RecyclerView.Adapter<ImagesAdapter.Item>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Item = Item(ItemImagesBinding.inflate(parent.context.layoutInflater()))

    override fun getItemCount(): Int {
        return imageList!!.size
    }

    override fun onBindViewHolder(holder: ImagesAdapter.Item, position: Int) {

        Glide.with(context)
            .load(imageList!![position])
            .into(holder.binding.ivImageItem)
    }

    class Item(val binding: ItemImagesBinding) : RecyclerView.ViewHolder(binding.root)

}