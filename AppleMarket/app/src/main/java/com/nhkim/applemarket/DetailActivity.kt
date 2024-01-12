package com.nhkim.applemarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nhkim.applemarket.ItemManager.itemList
import com.nhkim.applemarket.databinding.ActivityDetailBinding
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val itemPosition: Int by lazy {
        intent.getIntExtra(Constants.ITEM_INDEX,0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedItem = intent.getParcelableExtra<Item>(Constants.ITEM_OBJECT)


        binding.ivItemImage.setImageResource(receivedItem!!.itemImage)
        binding.tvSeller.text = receivedItem.seller
        binding.tvAdd.text = receivedItem.address
        binding.tvItemTitle.text = receivedItem.title
        binding.tvItemContent.text = receivedItem.content


        val formatter = DecimalFormat("#,###원")
        binding.tvItemPrice.text = formatter.format(receivedItem.price)

        binding.ivBackBtn.setOnClickListener {
            exit()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }


        // 상태에 따라 하트 아이콘 초기 설정
        if (receivedItem.isLike) {
            Toast.makeText(this, "좋아요 O", Toast.LENGTH_SHORT).show()
            binding.ivHeart.setImageResource(R.drawable.heart_full)
        } else {
            Toast.makeText(this, "좋아요 X", Toast.LENGTH_SHORT).show()
            binding.ivHeart.setImageResource(R.drawable.heart_empty)
        }

        binding.lyHeart.setOnClickListener {
            heartOnClickListeners(receivedItem)
        }
    }

    private fun heartOnClickListeners(receivedItem: Item) {
        if(!receivedItem.isLike){
            Toast.makeText(this, "관심 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            binding.ivHeart.setImageResource(R.drawable.heart_full)
            receivedItem.isLike = true
            Toast.makeText(this, receivedItem.isLike.toString(), Toast.LENGTH_SHORT).show()
            itemList[itemPosition].isLike = true
            itemList[itemPosition].like += 1
        }
        else{
            binding.ivHeart.setImageResource(R.drawable.heart_empty)
            receivedItem.isLike = false
            Toast.makeText(this, receivedItem.isLike.toString(), Toast.LENGTH_SHORT).show()
            itemList[itemPosition].isLike = false
            itemList[itemPosition].like -= 1
        }
    }

    fun exit() {
        if (!isFinishing) finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}