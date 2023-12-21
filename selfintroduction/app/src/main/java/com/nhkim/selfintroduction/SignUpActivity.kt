package com.nhkim.selfintroduction

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import org.w3c.dom.Text

class SignUpActivity : AppCompatActivity() {

    private val etName: EditText by lazy {
        findViewById(R.id.et_name)
    }
    private val tvNameError: TextView by lazy {
        findViewById(R.id.tv_name_error)
    }
    private val etEmail: EditText by lazy {
        findViewById(R.id.et_email)
    }
    private val tvEmailError: TextView by lazy {
        findViewById(R.id.tv_email_error)
    }
    private val serviceProvider: Spinner by lazy {
        findViewById(R.id.service_provider)
    }

    private val editTexts
        get() = listOf(
            etName,etEmail
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initView()

    }

    private fun initView(){
        setEditTextListener()
        setServiceProvider()
    }

    private fun setServiceProvider() {
        serviceProvider.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf(
                getString(R.string.sign_up_email_provider_gmail),
                getString(R.string.sign_up_email_provider_kakao),
                getString(R.string.sign_up_email_provider_naver),
                getString(R.string.sign_up_email_provider_direct)

            )
        )

        serviceProvider.onItemClickListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) = Unit

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

        }
    }

    //editTexts들을 순회하면서 addTextChangedListener와 setOnFocusChangeListener를 등록
    private fun setEditTextListener(){
        editTexts.forEach { editText ->
            editText.addTextChangedListener{
                editText.setErrorMessage()
            }

            editText.setOnFocusChangeListener{ v, hasFocous ->
                if(hasFocous.not()){
                    editText.setErrorMessage()
                }
            }
        }
    }

    private fun EditText.setErrorMessage(){
        when(this){
            etName -> {
                tvNameError.text = getMessageValidName()
            }
            etEmail -> {
                tvEmailError.text = getMessageValidEmail()
            }
        }
    }

    private fun getMessageValidName(): String = if(etName.text.toString().isBlank()){
        getString(R.string.sign_up_name_error)

    }else{
        ""
    }

    private fun getMessageValidEmail(): String = if(etEmail.text.toString().isBlank()){
        getString(R.string.sign_up_email_error_blank)

    }else{
        ""
    }

    fun isPossibleSignUp(isNameEnable: Boolean, isEmailEnable: Boolean, isPasswordEnable: Boolean, isPasswordCheckEnable: Boolean): Boolean{
        return isNameEnable && isEmailEnable && isPasswordEnable && isPasswordCheckEnable
    }

}