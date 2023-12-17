package com.nhkim.selfintroduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.w3c.dom.Text

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val nameEditText = findViewById<EditText>(R.id.signUpNameEditText)
        val idEditText = findViewById<EditText>(R.id.signUpIdEditText)
        val passwordEditText = findViewById<EditText>(R.id.signUpPasswordEditText)
        val signupBtn = findViewById<Button>(R.id.SignUpSignUpBtn)

        signupBtn.setOnClickListener {

            val userName = nameEditText.text.toString()
            val userId = idEditText.text.toString()
            val userPassword = passwordEditText.text.toString()

            if(userName == "" || userId == "" || userPassword == ""){
                Toast.makeText(this, "입력되지 않은 정보가 있습니다", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("userName", userName)
                intent.putExtra("userId", userId)
                intent.putExtra("userPassword", userPassword)
                startActivity(intent)
            }

        }
    }
}