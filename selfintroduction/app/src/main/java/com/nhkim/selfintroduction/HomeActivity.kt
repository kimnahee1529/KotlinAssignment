package com.nhkim.selfintroduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.util.Random

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val imageList = listOf(R.drawable.codinge1, R.drawable.codinge2,R.drawable.codinge3, R.drawable.codinge4, R.drawable.codinge5)

        val idText = findViewById<TextView>(R.id.homeId)
        val nameText = findViewById<TextView>(R.id.homeName)
        val finishBtn = findViewById<Button>(R.id.finishBtn)
        val profileImage = findViewById<ImageView>(R.id.homeImageView)
        val random: Random = Random()
        val num = random.nextInt(5)

        val userId = intent.getStringExtra("userId")
        val userName = intent.getStringExtra("userName")

        idText.text = "아이디 : $userId"
        if(userName != null){
            nameText.text = "이름 : $userName"
        }

        profileImage.setImageResource(imageList[num])

        finishBtn.setOnClickListener {
            finish()
        }

    }
}