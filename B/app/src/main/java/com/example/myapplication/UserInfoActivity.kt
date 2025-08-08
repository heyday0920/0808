package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UserInfoActivity : AppCompatActivity() {
    
    private lateinit var lastNameInput: EditText
    private lateinit var firstNameInput: EditText
    private lateinit var nicknameInput: EditText
    private lateinit var nextButton: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("UserInfoActivity", "onCreate called")
        setContentView(R.layout.activity_user_info)
        
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        lastNameInput = findViewById(R.id.lastNameInput)
        firstNameInput = findViewById(R.id.firstNameInput)
        nicknameInput = findViewById(R.id.nicknameInput)
        nextButton = findViewById(R.id.nextButton)
    }
    
    private fun setupClickListeners() {
        nextButton.setOnClickListener {
            validateAndProceed()
        }
    }
    
    private fun validateAndProceed() {
        val lastName = lastNameInput.text.toString().trim()
        val firstName = firstNameInput.text.toString().trim()
        val nickname = nicknameInput.text.toString().trim()
        
        // Basic validation
        if (lastName.isEmpty()) {
            Toast.makeText(this, "姓を入力してください", Toast.LENGTH_SHORT).show()
            lastNameInput.requestFocus()
            return
        }
        
        if (firstName.isEmpty()) {
            Toast.makeText(this, "名を入力してください", Toast.LENGTH_SHORT).show()
            firstNameInput.requestFocus()
            return
        }
        
        if (nickname.isEmpty()) {
            Toast.makeText(this, "ニックネームを入力してください", Toast.LENGTH_SHORT).show()
            nicknameInput.requestFocus()
            return
        }
        
        // All validations passed, proceed to next screen
        android.util.Log.d("UserInfoActivity", "User info validated, proceeding to next screen")
        
        // Save user info (you can use SharedPreferences or pass via Intent)
        val userInfo = mapOf(
            "lastName" to lastName,
            "firstName" to firstName,
            "nickname" to nickname
        )
        
        // For now, just show a toast and navigate to GenderSelectionActivity
        Toast.makeText(this, "ユーザー情報を保存しました", Toast.LENGTH_SHORT).show()
        
        val intent = Intent(this, GenderSelectionActivity::class.java)
        startActivity(intent)
        finish() // Close this activity so user can't go back
    }
} 