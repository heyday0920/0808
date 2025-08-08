package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class BirthdayInputActivity : AppCompatActivity() {
    
    private lateinit var birthdayInput: EditText
    private lateinit var nextButton: Button
    
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("BirthdayInputActivity", "onCreate called")
        setContentView(R.layout.activity_birthday_input)
        
        initializeViews()
        setupClickListeners()
        
        // Set default date to 2000/09/23
        setDefaultDate()
    }
    
    private fun initializeViews() {
        birthdayInput = findViewById(R.id.birthdayInput)
        nextButton = findViewById(R.id.nextButton)
    }
    
    private fun setupClickListeners() {
        // Birthday input field click
        birthdayInput.setOnClickListener {
            showDatePicker()
        }
        
        // Next button
        nextButton.setOnClickListener {
            proceedToNext()
        }
    }
    
    private fun setDefaultDate() {
        // Set default date to 2000/09/23
        calendar.set(2000, Calendar.SEPTEMBER, 23)
        birthdayInput.setText(dateFormat.format(calendar.time))
    }
    
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                birthdayInput.setText(dateFormat.format(calendar.time))
                android.util.Log.d("BirthdayInputActivity", "Date selected: ${dateFormat.format(calendar.time)}")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        
        // Set reasonable date range (e.g., 1900 to current year)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.datePicker.minDate = Calendar.getInstance().apply {
            set(1900, 0, 1)
        }.timeInMillis
        
        datePickerDialog.show()
    }
    
    private fun proceedToNext() {
        val birthday = birthdayInput.text.toString().trim()
        
        if (birthday.isEmpty()) {
            Toast.makeText(this, "生年月日を入力してください", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Basic date format validation
        if (!isValidDateFormat(birthday)) {
            Toast.makeText(this, "正しい日付形式で入力してください (YYYY/MM/DD)", Toast.LENGTH_SHORT).show()
            return
        }
        
        android.util.Log.d("BirthdayInputActivity", "Proceeding to next screen with birthday: $birthday")
        
        // Save birthday (you can use SharedPreferences or pass via Intent)
        val userInfo = mapOf(
            "birthday" to birthday
        )
        
        // For now, just show a toast and navigate to AreaSelectionActivity
        Toast.makeText(this, "生年月日を保存しました: $birthday", Toast.LENGTH_SHORT).show()
        
        val intent = Intent(this, AreaSelectionActivity::class.java)
        startActivity(intent)
        finish() // Close this activity so user can't go back
    }
    
    private fun isValidDateFormat(dateString: String): Boolean {
        return try {
            dateFormat.parse(dateString)
            true
        } catch (e: Exception) {
            false
        }
    }
} 