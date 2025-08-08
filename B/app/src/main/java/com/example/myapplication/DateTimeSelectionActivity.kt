package com.example.myapplication

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DateTimeSelectionActivity : AppCompatActivity() {
    
    private lateinit var closeButton: ImageView
    private lateinit var monthHeader: TextView
    private lateinit var clearButton: Button
    private lateinit var doneButton: Button
    private lateinit var startTimeSelector: TextView
    
    // Date views
    private lateinit var dateViews: List<TextView>
    
    private var selectedDate: String? = null
    private var selectedReservationTime: String? = null
    private var courseName: String? = null
    private var courseDuration: String? = null
    private var coursePrice: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time_selection)
        
        // コース情報を受け取る
        courseName = intent.getStringExtra("courseName")
        courseDuration = intent.getStringExtra("courseDuration")
        coursePrice = intent.getStringExtra("coursePrice")
        
        initializeViews()
        setupClickListeners()
        setupDateViews()
        setupDefaultTimeText()
        updateDoneButtonText()
        updateTitleWithCourseInfo()
    }
    
    private fun initializeViews() {
        closeButton = findViewById(R.id.closeButton)
        monthHeader = findViewById(R.id.monthHeader)
        clearButton = findViewById(R.id.clearButton)
        doneButton = findViewById(R.id.doneButton)
        startTimeSelector = findViewById(R.id.startTimeSelector)
    }
    
    private fun setupClickListeners() {
        // Close button
        closeButton.setOnClickListener {
            finish()
        }
        
        // Clear button
        clearButton.setOnClickListener {
            clearAllSelections()
        }
        
        // Done button
        doneButton.setOnClickListener {
            applySelections()
        }
        
        // Time selector
        startTimeSelector.setOnClickListener {
            showTimePickerDialog()
        }
    }
    
    private fun setupDateViews() {
        // Initialize date views
        dateViews = listOf(
            findViewById(R.id.date_1),
            findViewById(R.id.date_2),
            findViewById(R.id.date_8),
            findViewById(R.id.date_9),
            findViewById(R.id.date_10),
            findViewById(R.id.date_11),
            findViewById(R.id.date_12),
            findViewById(R.id.date_13),
            findViewById(R.id.date_14),
            findViewById(R.id.date_15),
            findViewById(R.id.date_16),
            findViewById(R.id.date_17),
            findViewById(R.id.date_18),
            findViewById(R.id.date_19),
            findViewById(R.id.date_20),
            findViewById(R.id.date_21),
            findViewById(R.id.date_22),
            findViewById(R.id.date_23),
            findViewById(R.id.date_24),
            findViewById(R.id.date_25),
            findViewById(R.id.date_26),
            findViewById(R.id.date_27),
            findViewById(R.id.date_28)
        )
        
        // Set click listeners for date views
        dateViews.forEach { dateView ->
            dateView.setOnClickListener {
                selectDate(dateView)
            }
        }
        
        // Set default selection to date 1 (February 1st)
        selectDate(dateViews[0])
    }
    
    private fun setupDefaultTimeText() {
        startTimeSelector.text = "予約時間を選択"
        startTimeSelector.setTextColor(getColor(R.color.text_hint))
    }
    
    private fun updateTitleWithCourseInfo() {
        if (courseName != null) {
            val titleText = "$courseName の予約"
            val titleTextView = findViewById<TextView>(R.id.titleText)
            if (titleTextView != null) {
                titleTextView.text = titleText
            }
        }
    }
    
    private fun selectDate(dateView: TextView) {
        // Reset all date backgrounds
        dateViews.forEach { view ->
            view.background = null
        }
        
        // Set selected date background
        dateView.setBackgroundResource(R.drawable.date_selected_background)
        
        selectedDate = dateView.text.toString()
        updateDoneButtonText()
        android.util.Log.d("DateTimeSelectionActivity", "Date selected: $selectedDate")
    }
    
    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        
        // 30分刻みに調整
        val adjustedMinute = (minute / 30) * 30
        val adjustedHour = if (adjustedMinute == 60) hour + 1 else hour
        
        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                // 9:00-22:00の範囲内かチェック
                val totalMinutes = selectedHour * 60 + selectedMinute
                val minMinutes = 9 * 60 // 9:00
                val maxMinutes = 22 * 60 // 22:00
                
                if (totalMinutes < minMinutes || totalMinutes > maxMinutes) {
                    Toast.makeText(this, "予約時間は9:00から22:00の間で選択してください", Toast.LENGTH_SHORT).show()
                    return@TimePickerDialog
                }
                
                // 30分刻みに調整
                val adjustedSelectedMinute = (selectedMinute / 30) * 30
                val adjustedSelectedHour = if (adjustedSelectedMinute == 60) selectedHour + 1 else selectedHour
                val finalMinute = if (adjustedSelectedMinute == 60) 0 else adjustedSelectedMinute
                val finalHour = if (adjustedSelectedMinute == 60) adjustedSelectedHour else selectedHour
                
                val timeString = String.format("%02d:%02d", finalHour, finalMinute)
                
                selectedReservationTime = timeString
                startTimeSelector.text = timeString
                startTimeSelector.setTextColor(getColor(R.color.text_primary))
                
                updateDoneButtonText()
                android.util.Log.d("DateTimeSelectionActivity", "Reservation time selected: $timeString")
            },
            adjustedHour,
            adjustedMinute,
            true // 24-hour format
        )
        
        timePickerDialog.show()
    }
    
    private fun updateDoneButtonText() {
        val count = calculateSelectedCount()
        doneButton.text = "完了 (${count}件)"
    }
    
    private fun calculateSelectedCount(): Int {
        var count = 0
        if (selectedDate != null) count++
        if (selectedReservationTime != null) count++
        return count
    }
    
    private fun clearAllSelections() {
        // Clear date selection
        dateViews.forEach { view ->
            view.background = null
        }
        selectedDate = null
        
        // Clear time selection
        startTimeSelector.text = "予約時間を選択"
        startTimeSelector.setTextColor(getColor(R.color.text_hint))
        selectedReservationTime = null
        
        updateDoneButtonText()
        Toast.makeText(this, "すべての選択をクリアしました", Toast.LENGTH_SHORT).show()
    }
    
    private fun applySelections() {
        val resultIntent = Intent().apply {
            putExtra("selectedDate", selectedDate)
            putExtra("selectedReservationTime", selectedReservationTime)
        }
        
        setResult(RESULT_OK, resultIntent)
        finish()
        
        Toast.makeText(this, "日時を適用しました", Toast.LENGTH_SHORT).show()
    }
} 