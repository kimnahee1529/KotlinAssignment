package com.nhkim.selfintroduction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import org.w3c.dom.Text

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val idText = findViewById<TextView>(R.id.homeId)
        val nameText = findViewById<TextView>(R.id.homeName)
        val finishBtn = findViewById<ConstraintLayout>(R.id.finishBtn)

        val userId = intent.getStringExtra("userId")
        val userName = intent.getStringExtra("userName")
        idText.text = "아이디 : $userId"
        if(userName != null){
            nameText.text = "이름 : $userName"
        }

        finishBtn.setOnClickListener {
            finish()
        }

    }
}