package com.nhkim.selfintroduction

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import org.w3c.dom.Text

class SignInActivity : AppCompatActivity() {

    private lateinit var idText: EditText
    private lateinit var passwordText: EditText

    private val signUpActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            data?.let { data -> //data가 null이 아닐 때 실행
                val userId = data.getStringExtra("userId") ?: ""
                val userPassword = data.getStringExtra("userPassword") ?: ""

                idText.setText(userId)
                passwordText.setText(userPassword)
            }

            //let을 쓰지 않았을 때
//            if (data != null) { // data가 null이 아닐 때 실행
//                val userId = data.getStringExtra("userId") ?: ""
//                val userPassword = data.getStringExtra("userPassword") ?: ""
//
//                idText.setText(userId)
//                passwordText.setText(userPassword)
//            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        idText = findViewById(R.id.signInIdEditText)
        passwordText = findViewById(R.id.signInPasswordEditText)

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
            signUpActivityResultLauncher.launch(intent)
        }
    }
}