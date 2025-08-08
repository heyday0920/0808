package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PlanDetailActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("PlanDetailActivity", "onCreate called")
        
        setContentView(R.layout.activity_plan_detail)
        
        setupHeaderMenu()
        setupBottomNavigation()
    }
    
    private fun setupHeaderMenu() {
        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish() // 前のページに戻る
        }
    }
    
    private fun setupBottomNavigation() {
        val bottomSearch = findViewById<LinearLayout>(R.id.bottom_search)
        val bottomHome = findViewById<LinearLayout>(R.id.bottom_home)
        val bottomChat = findViewById<LinearLayout>(R.id.bottom_chat)
        val bottomProfile = findViewById<LinearLayout>(R.id.bottom_profile)
        
        val navigationHelper = BottomNavigationHelper(this)
        navigationHelper.setupBottomNavigation(
            bottomSearch, bottomHome, bottomChat, bottomProfile, "home"
        )
    }
} 