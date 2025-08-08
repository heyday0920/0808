package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    
    private lateinit var startButton: Button
    private lateinit var loginButton: Button
    private lateinit var privacyPolicyLink: TextView
    private lateinit var pointsGuidelineLink: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("SplashActivity", "onCreate called")
        setContentView(R.layout.activity_splash)
        
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        startButton = findViewById(R.id.startButton)
        loginButton = findViewById(R.id.loginButton)
        privacyPolicyLink = findViewById(R.id.privacyPolicyLink)
        pointsGuidelineLink = findViewById(R.id.pointsGuidelineLink)
    }
    
    private fun setupClickListeners() {
        // Start Beauvidual Button - Navigate to Sign Up
        startButton.setOnClickListener {
            android.util.Log.d("SplashActivity", "Start button clicked")
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        
        // Login Button - Navigate to Top Activity
        loginButton.setOnClickListener {
            android.util.Log.d("SplashActivity", "Login button clicked")
            try {
                val intent = Intent(this, TopActivity::class.java)
                startActivity(intent)
                finish() // SplashActivityを終了
            } catch (e: Exception) {
                android.util.Log.e("SplashActivity", "Error starting TopActivity: ${e.message}")
                Toast.makeText(this, "エラーが発生しました", Toast.LENGTH_SHORT).show()
            }
        }
        
        // Privacy Policy Link
        privacyPolicyLink.setOnClickListener {
            Toast.makeText(this, "プライバシーポリシーを開きます", Toast.LENGTH_SHORT).show()
        }
        
        // Points Guideline Link
        pointsGuidelineLink.setOnClickListener {
            Toast.makeText(this, "ポイントガイドラインを開きます", Toast.LENGTH_SHORT).show()
        }
    }
} 