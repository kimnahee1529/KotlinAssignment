package com.nhkim.imagecollector.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FormatManager {
    fun formatDateToString(date: Date): String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun ImageView.loadImage(url: String){
        Glide.with(this)
            .load(url)
            .into(this)
    }
}