package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {
    
    private lateinit var appleSignupButton: Button
    private lateinit var lineSignupButton: Button
    private lateinit var emailSignupButton: Button
    private lateinit var helpLink: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("SignupActivity", "onCreate called")
        setContentView(R.layout.activity_signup)
        
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        appleSignupButton = findViewById(R.id.appleSignupButton)
        lineSignupButton = findViewById(R.id.lineSignupButton)
        emailSignupButton = findViewById(R.id.emailSignupButton)
        helpLink = findViewById(R.id.helpLink)
    }
    
    private fun setupClickListeners() {
        // Apple Sign Up Button
        appleSignupButton.setOnClickListener {
            android.util.Log.d("SignupActivity", "Apple signup button clicked")
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
        
        // LINE Sign Up Button
        lineSignupButton.setOnClickListener {
            android.util.Log.d("SignupActivity", "LINE signup button clicked")
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
        
        // Email Sign Up Button
        emailSignupButton.setOnClickListener {
            android.util.Log.d("SignupActivity", "Email signup button clicked")
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
        
        // Help Link
        helpLink.setOnClickListener {
            android.util.Log.d("SignupActivity", "Help link clicked")
            Toast.makeText(this, "ヘルプ・よくある質問を開きます", Toast.LENGTH_SHORT).show()
            // ここでヘルプ画面を開く処理を実装
        }
    }
} 