package com.nhkim.imagecollector.ui.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nhkim.imagecollector.utils.FormatManager
import com.nhkim.imagecollector.utils.FormatManager.loadImage
import com.nhkim.imagecollector.R
import com.nhkim.imagecollector.data.image.Document
import com.nhkim.imagecollector.databinding.RecyclerViewItemBinding

class FavoriteImageAdapter() : ListAdapter<Document, FavoriteImageAdapter.Holder>(
    DocumentDiffCallback()
) {

    interface FavoriteItemClick {
        fun onDeleteClick(view: View, position: Int)
    }

    var favoriteItemClick: FavoriteItemClick? = null

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
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position) // ListAdapter에서는 getItem() 메서드 사용
        holder.bind(item)

        //여기서 isHearted 변수 상태 바꿔야하나?

    }

    inner class Holder(private val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(document: Document) {
            val imageUrl = document.thumbnail_url ?: "defaultImageUrl"
            binding.ivThumbnail.loadImage(imageUrl)

            Log.d("Adapter썸네일", document.thumbnail_url)
            binding.ivThumbnail.clipToOutline = true

            binding.tvSiteName.text = document.display_sitename
            FormatManager.formatDateToString(document.datetime)
            binding.ivHeart.isVisible = document.isHearted

//          // 하트 이미지 클릭 리스너 설정
            binding.root.setOnClickListener {
                document.isHearted = !document.isHearted
                binding.ivHeart.setImageResource(R.drawable.full_heart)
                favoriteItemClick?.onDeleteClick(it, position)
            }
        }
    }

    fun setItemClick(listener: FavoriteItemClick) {
        favoriteItemClick = listener
    }

}
