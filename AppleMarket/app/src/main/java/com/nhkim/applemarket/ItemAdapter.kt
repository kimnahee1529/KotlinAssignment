package com.nhkim.applemarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nhkim.applemarket.databinding.ItemRecyclerviewBinding
import java.text.DecimalFormat

class ItemAdapter(val item: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.Holder>() {

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.Holder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.Holder, position: Int) {
        val item = item[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it, position)
        }
        holder.itemView.clipToOutline = true

    }

    override fun getItemCount(): Int = item.size

    inner class Holder(binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {

        private val title = binding.tvItemTitle
        private val address = binding.tvItemAd
        private val price = binding.tvItemPrice
        private val itemImage = binding.ivItemImage
        private val heart = binding.tvHeartNum
        private val chat = binding.tvChatNum

        fun bind(item: Item){
            title.text = item.title
            address.text = item.address

            val formatter = DecimalFormat("#,###원")
            price.text = formatter.format(item.price)

            itemImage.setImageResource(item.itemImage)
            heart.text = item.like.toString()
            chat.text = item.chat.toString()
        }
    }
}