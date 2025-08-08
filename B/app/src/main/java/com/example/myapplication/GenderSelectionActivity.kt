package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GenderSelectionActivity : AppCompatActivity() {
    
    private lateinit var femaleButton: Button
    private lateinit var maleButton: Button
    private lateinit var otherButton: Button
    private lateinit var nextButton: Button
    
    private var selectedGender: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("GenderSelectionActivity", "onCreate called")
        setContentView(R.layout.activity_gender_selection)
        
        initializeViews()
        setupClickListeners()
        
        // Set default selection to female
        selectGender("女性")
    }
    
    private fun initializeViews() {
        femaleButton = findViewById(R.id.femaleButton)
        maleButton = findViewById(R.id.maleButton)
        otherButton = findViewById(R.id.otherButton)
        nextButton = findViewById(R.id.nextButton)
    }
    
    private fun setupClickListeners() {
        // Gender selection buttons
        femaleButton.setOnClickListener {
            selectGender("女性")
        }
        
        maleButton.setOnClickListener {
            selectGender("男性")
        }
        
        otherButton.setOnClickListener {
            selectGender("その他")
        }
        
        // Next button
        nextButton.setOnClickListener {
            proceedToNext()
        }
    }
    
    private fun selectGender(gender: String) {
        selectedGender = gender
        
        // Reset all buttons to unselected state
        femaleButton.setBackgroundResource(R.drawable.gender_unselected_background)
        maleButton.setBackgroundResource(R.drawable.gender_unselected_background)
        otherButton.setBackgroundResource(R.drawable.gender_unselected_background)
        
        // Set selected button background
        when (gender) {
            "女性" -> femaleButton.setBackgroundResource(R.drawable.gender_selected_background)
            "男性" -> maleButton.setBackgroundResource(R.drawable.gender_selected_background)
            "その他" -> otherButton.setBackgroundResource(R.drawable.gender_selected_background)
        }
        
        android.util.Log.d("GenderSelectionActivity", "Gender selected: $gender")
    }
    
    private fun proceedToNext() {
        if (selectedGender == null) {
            Toast.makeText(this, "性別を選択してください", Toast.LENGTH_SHORT).show()
            return
        }
        
        android.util.Log.d("GenderSelectionActivity", "Proceeding to next screen with gender: $selectedGender")
        
        // Save gender selection (you can use SharedPreferences or pass via Intent)
        val userInfo = mapOf(
            "gender" to selectedGender
        )
        
        // For now, just show a toast and navigate to BirthdayInputActivity
        Toast.makeText(this, "性別を保存しました: $selectedGender", Toast.LENGTH_SHORT).show()
        
        val intent = Intent(this, BirthdayInputActivity::class.java)
        startActivity(intent)
        finish() // Close this activity so user can't go back
    }
} 