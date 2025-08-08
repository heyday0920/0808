package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AreaSelectionActivity : AppCompatActivity() {
    
    private lateinit var closeButton: ImageView
    private lateinit var searchEditText: EditText
    private lateinit var selectAreaOption: TextView
    private lateinit var selectStationOption: TextView
    private lateinit var currentLocationOption: TextView
    private lateinit var noSpecificationOption: TextView
    private lateinit var clearButton: Button
    private lateinit var doneButton: Button
    
    private var selectedArea: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("AreaSelectionActivity", "onCreate called")
        setContentView(R.layout.activity_area_selection)
        
        initializeViews()
        setupClickListeners()
        updateDoneButton()
    }
    
    private fun initializeViews() {
        closeButton = findViewById(R.id.close_button)
        searchEditText = findViewById(R.id.search_edit_text)
        selectAreaOption = findViewById(R.id.select_area_option)
        selectStationOption = findViewById(R.id.select_station_option)
        currentLocationOption = findViewById(R.id.current_location_option)
        noSpecificationOption = findViewById(R.id.no_specification_option)
        clearButton = findViewById(R.id.clear_button)
        doneButton = findViewById(R.id.done_button)
    }
    
    private fun setupClickListeners() {
        // Close button
        closeButton.setOnClickListener {
            finish()
        }
        
        // Area selection options
        selectAreaOption.setOnClickListener {
            selectOption("エリアを選ぶ")
        }
        
        selectStationOption.setOnClickListener {
            selectOption("駅を選ぶ")
        }
        
        currentLocationOption.setOnClickListener {
            selectOption("現在地周辺")
        }
        
        noSpecificationOption.setOnClickListener {
            selectOption("指定しない")
        }
        
        // Clear button
        clearButton.setOnClickListener {
            clearSelection()
        }
        
        // Done button
        doneButton.setOnClickListener {
            finishSelection()
        }
    }
    
    private fun selectOption(option: String) {
        selectedArea = option
        
        // Reset all options background
        resetOptionsBackground()
        
        // Highlight selected option
        when (option) {
            "エリアを選ぶ" -> selectAreaOption.setBackgroundColor(getColor(R.color.primary_purple_light))
            "駅を選ぶ" -> selectStationOption.setBackgroundColor(getColor(R.color.primary_purple_light))
            "現在地周辺" -> currentLocationOption.setBackgroundColor(getColor(R.color.primary_purple_light))
            "指定しない" -> noSpecificationOption.setBackgroundColor(getColor(R.color.primary_purple_light))
        }
        
        updateDoneButton()
        android.util.Log.d("AreaSelectionActivity", "Area option selected: $option")
    }
    
    private fun resetOptionsBackground() {
        selectAreaOption.background = getDrawable(android.R.attr.selectableItemBackground)
        selectStationOption.background = getDrawable(android.R.attr.selectableItemBackground)
        currentLocationOption.background = getDrawable(android.R.attr.selectableItemBackground)
        noSpecificationOption.background = getDrawable(android.R.attr.selectableItemBackground)
    }
    
    private fun clearSelection() {
        selectedArea = null
        resetOptionsBackground()
        searchEditText.text.clear()
        updateDoneButton()
        Toast.makeText(this, "選択をクリアしました", Toast.LENGTH_SHORT).show()
    }
    
    private fun updateDoneButton() {
        if (selectedArea != null) {
            doneButton.text = "完了（1件）"
            doneButton.isEnabled = true
            doneButton.alpha = 1.0f
        } else {
            doneButton.text = "完了（0件）"
            doneButton.isEnabled = false
            doneButton.alpha = 0.5f
        }
    }
    
    private fun finishSelection() {
        if (selectedArea == null) {
            Toast.makeText(this, "エリアを選択してください", Toast.LENGTH_SHORT).show()
            return
        }
        
        android.util.Log.d("AreaSelectionActivity", "Finishing selection with area: $selectedArea")
        
        // Return result to the calling activity
        val resultIntent = Intent().apply {
            putExtra("selected_area", selectedArea)
        }
        
        setResult(RESULT_OK, resultIntent)
        finish()
        
        Toast.makeText(this, "エリアを選択しました: $selectedArea", Toast.LENGTH_SHORT).show()
    }
} 