package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuSelectionActivity : AppCompatActivity() {
    
    private lateinit var relaxButton: Button
    private lateinit var estheticButton: Button
    private lateinit var nailButton: Button
    private lateinit var eyelashButton: Button
    private lateinit var hairRemovalButton: Button
    private lateinit var massageButton: Button
    private lateinit var nextButton: Button
    
    private var selectedMenu: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("MenuSelectionActivity", "onCreate called")
        setContentView(R.layout.activity_menu_selection)
        
        initializeViews()
        setupClickListeners()
        
        // Set default selection to Massage
        selectMenu("マッサージ")
    }
    
    private fun initializeViews() {
        relaxButton = findViewById(R.id.relaxButton)
        estheticButton = findViewById(R.id.estheticButton)
        nailButton = findViewById(R.id.nailButton)
        eyelashButton = findViewById(R.id.eyelashButton)
        hairRemovalButton = findViewById(R.id.hairRemovalButton)
        massageButton = findViewById(R.id.massageButton)
        nextButton = findViewById(R.id.nextButton)
    }
    
    private fun setupClickListeners() {
        // Menu selection buttons
        relaxButton.setOnClickListener {
            selectMenu("リラク")
        }
        
        estheticButton.setOnClickListener {
            selectMenu("エステ")
        }
        
        nailButton.setOnClickListener {
            selectMenu("ネイル")
        }
        
        eyelashButton.setOnClickListener {
            selectMenu("まつげ")
        }
        
        hairRemovalButton.setOnClickListener {
            selectMenu("脱毛")
        }
        
        massageButton.setOnClickListener {
            selectMenu("マッサージ")
        }
        
        // Next button
        nextButton.setOnClickListener {
            proceedToNext()
        }
    }
    
    private fun selectMenu(menu: String) {
        selectedMenu = menu
        
        // Reset all buttons to unselected state
        relaxButton.setBackgroundResource(R.drawable.menu_unselected_background)
        estheticButton.setBackgroundResource(R.drawable.menu_unselected_background)
        nailButton.setBackgroundResource(R.drawable.menu_unselected_background)
        eyelashButton.setBackgroundResource(R.drawable.menu_unselected_background)
        hairRemovalButton.setBackgroundResource(R.drawable.menu_unselected_background)
        massageButton.setBackgroundResource(R.drawable.menu_unselected_background)
        
        // Set selected button background
        when (menu) {
            "リラク" -> relaxButton.setBackgroundResource(R.drawable.menu_selected_background)
            "エステ" -> estheticButton.setBackgroundResource(R.drawable.menu_selected_background)
            "ネイル" -> nailButton.setBackgroundResource(R.drawable.menu_selected_background)
            "まつげ" -> eyelashButton.setBackgroundResource(R.drawable.menu_selected_background)
            "脱毛" -> hairRemovalButton.setBackgroundResource(R.drawable.menu_selected_background)
            "マッサージ" -> massageButton.setBackgroundResource(R.drawable.menu_selected_background)
        }
        
        android.util.Log.d("MenuSelectionActivity", "Menu selected: $menu")
    }
    
    private fun proceedToNext() {
        if (selectedMenu == null) {
            Toast.makeText(this, "メニューを選択してください", Toast.LENGTH_SHORT).show()
            return
        }
        
        android.util.Log.d("MenuSelectionActivity", "Proceeding to next screen with menu: $selectedMenu")
        
        // Save menu selection (you can use SharedPreferences or pass via Intent)
        val userInfo = mapOf(
            "menu" to selectedMenu
        )
        
        // For now, just show a toast and navigate to TopActivity
        Toast.makeText(this, "メニューを保存しました: $selectedMenu", Toast.LENGTH_SHORT).show()
        
        val intent = Intent(this, TopActivity::class.java)
        startActivity(intent)
        finish() // Close this activity so user can't go back
    }
} 