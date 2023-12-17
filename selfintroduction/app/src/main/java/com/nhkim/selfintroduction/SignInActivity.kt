package com.nhkim.selfintroduction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.w3c.dom.Text

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val idText = findViewById<EditText>(R.id.signInIdEditText)
        val passwordText = findViewById<EditText>(R.id.signInPasswordEditText)
        val loginBtn = findViewById<Button>(R.id.signInLoginBtn)
        val signupBtn = findViewById<Button>(R.id.signInSignUpBtn)
        loginBtn.setOnClickListener {
            val userId = idText.text.toString()
            val userPassword = passwordText.text.toString()

            if(userId == "" || userPassword == ""){
                Toast.makeText(this, "아이디/비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
            } else{

                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("userId", userId)
                    putExtra("userPassword", userPassword)
                }
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }

        }

        signupBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}