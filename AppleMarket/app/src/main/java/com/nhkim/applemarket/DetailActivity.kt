package com.nhkim.applemarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nhkim.applemarket.databinding.ActivityDetailBinding
import com.nhkim.applemarket.databinding.ActivityMainBinding
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemKey = "Item"

        val receivedItem = intent.getParcelableExtra<Item>(itemKey)
        Log.d("받는Log",receivedItem?.itemImage.toString())


        binding.ivItemImage.setImageResource(receivedItem!!.itemImage)
        binding.tvSeller.text = receivedItem.seller
        binding.tvAdd.text = receivedItem.address
        binding.tvItemTitle.text = receivedItem.title
        binding.tvItemContent.text = receivedItem.content

        val formatter = DecimalFormat("#,###원")
        binding.tvItemPrice.text = formatter.format(receivedItem.price)

        binding.ivBackBtn.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}