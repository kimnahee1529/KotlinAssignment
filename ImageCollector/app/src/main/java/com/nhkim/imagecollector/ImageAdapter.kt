package com.nhkim.imagecollector

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nhkim.imagecollector.data.Document
import com.nhkim.imagecollector.databinding.RecyclerViewItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ImageAdapter: ListAdapter<Document, ImageAdapter.Holder>(DocumentDiffCallback()) {

    interface ItemClick{
        fun onClick(view: View, position: Int)
//        fun onLongClick(view: View, position: Int): Boolean
    }

    var itemClick: ItemClick? = null

    class DocumentDiffCallback : DiffUtil.ItemCallback<Document>() {
        override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
            // 객체의 고유 식별자를 비교
            return oldItem.thumbnail_url == newItem.thumbnail_url
        }

        override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position) // ListAdapter에서는 getItem() 메서드 사용
        holder.bind(item)
        holder.itemView.setOnClickListener{
            itemClick?.onClick(it, position)
        }
    }

    inner class Holder(private val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(document: Document){
             Glide.with(binding.root.context)
                 .load(document.thumbnail_url)
                 .into(binding.ivThumbnail)

            Log.d("Adapter썸네일", document.thumbnail_url)
            binding.ivThumbnail.clipToOutline = true

            binding.tvSiteName.text = document.display_sitename
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            binding.tvDateTime.text = formatter.format(document.datetime)
        }
    }

    fun getDocumentAtPosition(position: Int): Document? {
        return getItem(position)
    }
}