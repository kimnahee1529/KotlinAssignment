package com.nhkim.imagecollector.ui.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nhkim.imagecollector.utils.FormatManager
import com.nhkim.imagecollector.utils.FormatManager.loadImage
import com.nhkim.imagecollector.R
import com.nhkim.imagecollector.data.model.SearchItemModel
import com.nhkim.imagecollector.databinding.RecyclerViewItemBinding

class FavoriteImageAdapter() : ListAdapter<SearchItemModel, FavoriteImageAdapter.Holder>(
    DocumentDiffCallback()
) {

    interface FavoriteItemClick {
        fun onDeleteClick(view: View, position: Int)
    }

    var favoriteItemClick: FavoriteItemClick? = null

    class DocumentDiffCallback : DiffUtil.ItemCallback<SearchItemModel>() {
        override fun areItemsTheSame(oldItem: SearchItemModel, newItem: SearchItemModel): Boolean {
            // 객체의 고유 식별자를 비교
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: SearchItemModel, newItem: SearchItemModel): Boolean {
            return oldItem.title == newItem.title
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

        fun bind(document: SearchItemModel) {
            binding.tvType.text = document.type
            when(document.type){
                "Video"->{
                    binding.tvType.background.setTint(ContextCompat.getColor(binding.root.context, R.color.blue))
                }
                "Image"->{
                    binding.tvType.background.setTint(ContextCompat.getColor(binding.root.context, R.color.pink))
                }
            }
            binding.ivThumbnail.loadImage(document.url)

            Log.d("Adapter썸네일", document.url)
            binding.ivThumbnail.clipToOutline = true

            binding.tvSiteName.text = document.title
            FormatManager.formatDateToString(document.dateTime)
            binding.ivHeart.isVisible = document.isLike

//          // 하트 이미지 클릭 리스너 설정
            binding.root.setOnClickListener {
                document.isLike = !document.isLike
                binding.ivHeart.setImageResource(R.drawable.full_heart)
                favoriteItemClick?.onDeleteClick(it, position)
            }
        }
    }

    fun setItemClick(listener: FavoriteItemClick) {
        favoriteItemClick = listener
    }

}
