package com.nhkim.imagecollector.ui.search

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
import com.nhkim.imagecollector.data.Document
import com.nhkim.imagecollector.databinding.RecyclerViewItemBinding

class ImageSearchAdapter() : ListAdapter<Document, ImageSearchAdapter.Holder>(DocumentDiffCallback()) {

    interface SearchItemClick {
        fun onHeartClick(view: View, position: Int)
    }

    var searchItemClick: SearchItemClick? = null

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
            //TODO 함수로 빼기
//            Glide.with(binding.root.context)
//                .load(document.thumbnail_url)
//                .into(binding.ivThumbnail)
            binding.ivThumbnail.loadImage(document.thumbnail_url)

            Log.d("Adapter썸네일", document.thumbnail_url)
            binding.ivThumbnail.clipToOutline = true

            binding.tvSiteName.text = document.display_sitename
            //TODO 함수로 빼기

//            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//            binding.tvDateTime.text = formatter.format(document.datetime)
            FormatManager.formatDateToString(document.datetime)

            binding.ivHeart.isVisible = document.isHearted
//          // 하트 이미지 클릭 리스너 설정
            binding.root.setOnClickListener {
                document.isHearted = !document.isHearted
                binding.ivHeart.setImageResource(R.drawable.full_heart)
                binding.ivHeart.isVisible = document.isHearted
                searchItemClick?.onHeartClick(it, position)
            }
        }
    }

    fun getDocumentAtPosition(position: Int): Document? {
        return getItem(position)
    }

    fun setItemClick(listener: SearchItemClick) {
        searchItemClick = listener
    }
}
