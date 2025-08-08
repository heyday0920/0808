package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        // 3秒後にメイン画面に遷移（実際のログイン機能は後で実装）
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, TopActivity::class.java))
            finish()
        }, 3000)
    }
} 