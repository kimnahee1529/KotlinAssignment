package com.nhkim.selfintroduction

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import org.w3c.dom.Text

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val nameEditText = findViewById<EditText>(R.id.signUpNameEditText)
        val emailEditText = findViewById<EditText>(R.id.signUpEmailEditText)
        val passwordEditText = findViewById<EditText>(R.id.signUpPasswordEditText)
        val passwordCheckEditText = findViewById<EditText>(R.id.signUpPasswordCheckEditText)

        val signupBtn = findViewById<Button>(R.id.SignUpSignUpBtn)
        val nameWarning = findViewById<TextView>(R.id.signUpNameWarningText)
        val emailWarning = findViewById<TextView>(R.id.signUpEmailWarningText)
        val passWordWarning = findViewById<TextView>(R.id.signUpPasswordWarningText)
        val passWordCheckWarning = findViewById<TextView>(R.id.signUpPasswordCheckWarningText)

//        var userName = nameEditText.text.toString()
        lateinit var userName: String
        lateinit var useremail: String
        lateinit var userPassword: String
        lateinit var userPasswordCheck: String

        var isNameEnable = false
        var isEmailEnable = false
        var isPasswordEnable = false
        var isPasswordCheckEnable = false
        var isSignUpBtn = false
        Log.d("1isNameEnable", isNameEnable.toString())

        nameWarning.isVisible = false
        emailWarning.isVisible = false
        passWordWarning.isVisible = false
        passWordCheckWarning.isVisible = false

        nameEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                userName = p0.toString()
                isNameEnable = userName.isNotBlank()
                signupBtn.isEnabled = isPossibleSignUp(isNameEnable, isEmailEnable, isPasswordEnable, isPasswordCheckEnable)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("이름 바뀌기 전", p0.toString())
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("이름 바뀌었을 때", p0.toString())
            }
        })

        emailEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                useremail = p0.toString()
                isEmailEnable = useremail.isNotBlank()
                signupBtn.isEnabled = isPossibleSignUp(isNameEnable, isEmailEnable, isPasswordEnable, isPasswordCheckEnable)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("이메일 바뀌기 전", p0.toString())
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("이메일 바뀌었을 때", p0.toString())
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                userPassword = p0.toString()
                isPasswordEnable = userPassword.isNotBlank()
                signupBtn.isEnabled = isPossibleSignUp(isNameEnable, isEmailEnable, isPasswordEnable, isPasswordCheckEnable)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("이메일 바뀌기 전", p0.toString())
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("이메일 바뀌었을 때", p0.toString())
            }
        })

        passwordCheckEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                userPasswordCheck = p0.toString()
                isPasswordCheckEnable = userPasswordCheck.isNotBlank()
                signupBtn.isEnabled = isPossibleSignUp(isNameEnable, isEmailEnable, isPasswordEnable, isPasswordCheckEnable)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("이메일 바뀌기 전", p0.toString())
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("이메일 바뀌었을 때", p0.toString())
            }
        })

//        if(isNameEnable && isEmailEnable){
//            Log.d("마지막 isNameEnable", isNameEnable.toString())
//            Log.d("마지막 isEmailEnable", isEmailEnable.toString())
//            isSignUpBtn = true
//            Log.d("마지막 isSignUpBtn", isSignUpBtn.toString())
//        }

        signupBtn.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("userName", userName)
                putExtra("userId", useremail)
                putExtra("userPassword", userPassword)
                //apply를 쓰지 않았을 때
//                val resultIntent = Intent()
//                resultIntent.putExtra("userName", userName)
//                resultIntent.putExtra("userId", userId)
//                resultIntent.putExtra("userPassword", userPassword)
            }
            setResult(RESULT_OK, resultIntent)
            finish() // SignUpActivity 종료

        }
    }
    fun isPossiblePassword(password: String){

    }

    fun isPossibleSignUp(isNameEnable: Boolean, isEmailEnable: Boolean, isPasswordEnable: Boolean, isPasswordCheckEnable: Boolean): Boolean{
        return isNameEnable && isEmailEnable && isPasswordEnable && isPasswordCheckEnable
    }

}