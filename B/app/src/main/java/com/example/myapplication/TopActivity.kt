package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class RecentPractitioner(
    val id: String,
    val name: String,
    val imageUrl: String? = null
)

class TopActivity : AppCompatActivity() {
    
    private lateinit var searchEditText: EditText
    private lateinit var menuSelector: TextView
    private lateinit var dateSelector: TextView
    private lateinit var areaSelector: TextView
    private lateinit var searchButton: Button
    
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private val maxRecentPractitioners = 5
    
    // Activity result launchers
    private val menuFilterLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val selectedServices = result.data!!.getStringArrayExtra("selectedServices") ?: arrayOf()
            val keyword = result.data!!.getStringExtra("keyword") ?: ""
            val priceRange = result.data!!.getStringExtra("priceRange") ?: ""
            
            // Update menu selector text based on selected services
            if (selectedServices.isNotEmpty()) {
                val displayText = if (selectedServices.size <= 2) {
                    selectedServices.joinToString("、")
                } else {
                    "${selectedServices.take(2).joinToString("、")} 他${selectedServices.size - 2}件"
                }
                menuSelector.text = displayText
            } else {
                menuSelector.text = "未指定"
            }
            
            Toast.makeText(this, "フィルター条件を適用しました", Toast.LENGTH_SHORT).show()
        }
    }
    
    private val dateTimeLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val selectedDate = result.data!!.getStringExtra("selectedDate")
            val selectedStartTime = result.data!!.getStringExtra("selectedStartTime")
            val selectedEndTime = result.data!!.getStringExtra("selectedEndTime")
            
            // Update date selector text
            val displayText = when {
                selectedDate != null && selectedStartTime != null && selectedEndTime != null -> {
                    "2月${selectedDate}日 ${selectedStartTime}~${selectedEndTime}"
                }
                selectedDate != null -> {
                    "2月${selectedDate}日"
                }
                else -> "未指定"
            }
            dateSelector.text = displayText
            
            Toast.makeText(this, "日時を適用しました", Toast.LENGTH_SHORT).show()
        }
    }
    
    private val areaSelectionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val selectedArea = result.data!!.getStringExtra("selected_area")
            
            // Update area selector text
            if (selectedArea != null && selectedArea != "指定しない") {
                areaSelector.text = selectedArea
                areaSelector.setTextColor(getColor(R.color.text_primary))
            } else {
                areaSelector.text = "未指定"
                areaSelector.setTextColor(getColor(R.color.text_hint))
            }
            
            Toast.makeText(this, "エリアを適用しました", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("TopActivity", "onCreate called")
        try {
            android.util.Log.d("TopActivity", "Setting content view")
            setContentView(R.layout.top)
            
            android.util.Log.d("TopActivity", "Initializing SharedPreferences")
            sharedPreferences = getSharedPreferences("recent_practitioners", MODE_PRIVATE)
            
            android.util.Log.d("TopActivity", "Initializing views")
            initializeViews()
            
            android.util.Log.d("TopActivity", "Setting up click listeners")
            setupClickListeners()
            
            android.util.Log.d("TopActivity", "Setting up recent practitioners")
            setupRecentPractitioners()
            
            android.util.Log.d("TopActivity", "onCreate completed successfully")
        } catch (e: Exception) {
            android.util.Log.e("TopActivity", "Error in onCreate: ${e.message}")
            e.printStackTrace()
        }
    }
    
    private fun initializeViews() {
        try {
            searchEditText = findViewById(R.id.search_edittext)
            menuSelector = findViewById(R.id.menu_selector)
            dateSelector = findViewById(R.id.date_selector)
            areaSelector = findViewById(R.id.area_selector)
            searchButton = findViewById(R.id.search_button)
            
            android.util.Log.d("TopActivity", "Views initialized successfully")
        } catch (e: Exception) {
            android.util.Log.e("TopActivity", "Error initializing views: ${e.message}")
        }
    }
    
    private fun setupClickListeners() {
        try {
            android.util.Log.d("TopActivity", "Setting up selectors click listeners")
            // Menu selector
            menuSelector?.setOnClickListener {
                val intent = Intent(this, MenuFilterActivity::class.java)
                menuFilterLauncher.launch(intent)
            }
            
            // Date selector
            dateSelector?.setOnClickListener {
                val intent = Intent(this, DateTimeSelectionActivity::class.java)
                dateTimeLauncher.launch(intent)
            }
            
            // Area selector
            areaSelector?.setOnClickListener {
                val intent = Intent(this, AreaSelectionActivity::class.java)
                areaSelectionLauncher.launch(intent)
            }
            
            // Search button
            searchButton?.setOnClickListener {
                performSearch()
            }
            
            android.util.Log.d("TopActivity", "Setting up category buttons")
            setupCategoryButtons()
            
            android.util.Log.d("TopActivity", "Setting up footer navigation")
            setupFooterNavigation()
            
            android.util.Log.d("TopActivity", "Setting up header menu")
            setupHeaderMenu()
            
            android.util.Log.d("TopActivity", "Click listeners setup completed")
        } catch (e: Exception) {
            android.util.Log.e("TopActivity", "Error setting up click listeners: ${e.message}")
        }
    }
    
    private fun setupCategoryButtons() {
        try {
            findViewById<Button>(R.id.category_esthe)?.setOnClickListener {
                android.util.Log.d("TopActivity", "エステボタンがクリックされました")
                navigateToSearch("エステ")
            }
            
            findViewById<Button>(R.id.category_yoga)?.setOnClickListener {
                navigateToSearch("ヨガ")
            }
            
            findViewById<Button>(R.id.category_nail)?.setOnClickListener {
                navigateToSearch("ネイル")
            }
            
            findViewById<Button>(R.id.category_facial)?.setOnClickListener {
                navigateToSearch("フェイシャル")
            }
            
            findViewById<Button>(R.id.category_eyelash)?.setOnClickListener {
                navigateToSearch("まつげ")
            }
            
            findViewById<Button>(R.id.category_hair_removal)?.setOnClickListener {
                navigateToSearch("脱毛")
            }
        } catch (e: Exception) {
            android.util.Log.e("TopActivity", "Error setting up category buttons: ${e.message}")
        }
    }

    private fun setupFooterNavigation() {
        try {
            val bottomSearch = findViewById<LinearLayout>(R.id.bottom_search)
            val bottomHome = findViewById<LinearLayout>(R.id.bottom_home)
            val bottomChat = findViewById<LinearLayout>(R.id.bottom_chat)
            val bottomProfile = findViewById<LinearLayout>(R.id.bottom_profile)
            
            val navigationHelper = BottomNavigationHelper(this)
            navigationHelper.setupBottomNavigation(
                bottomSearch, bottomHome, bottomChat, bottomProfile, "search"
            )
        } catch (e: Exception) {
            android.util.Log.e("TopActivity", "Error setting up footer navigation: ${e.message}")
        }
    }
    
    private fun setupHeaderMenu() {
        try {
            val backButton = findViewById<ImageView>(R.id.back_button)
            backButton?.setOnClickListener {
                finish() // 前のページに戻る
            }
        } catch (e: Exception) {
            android.util.Log.e("TopActivity", "Error setting up header menu: ${e.message}")
        }
    }
    
    private fun performSearch() {
        val keyword = searchEditText?.text?.toString() ?: ""
        val menu = if (menuSelector?.text != "未指定") menuSelector?.text?.toString() ?: "" else ""
        val date = if (dateSelector?.text != "未指定") dateSelector?.text?.toString() ?: "" else ""
        val area = if (areaSelector?.text != "未指定") areaSelector?.text?.toString() ?: "" else ""
        
        val intent = Intent(this, PractitionerListActivity::class.java).apply {
            putExtra("keyword", keyword)
            putExtra("menu", menu)
            putExtra("date", date)
            putExtra("area", area)
        }
        startActivity(intent)
    }
    
    private fun navigateToSearch(category: String) {
        android.util.Log.d("TopActivity", "navigateToSearch called with category: $category")
        val intent = Intent(this, PractitionerListActivity::class.java).apply {
            putExtra("category", category)
        }
        android.util.Log.d("TopActivity", "Starting PractitionerListActivity")
        startActivity(intent)
    }
    
    private fun navigateToCalendar() {
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToPractitionerDetail() {
        val intent = Intent(this, PractitionerDetailActivity::class.java)
        startActivity(intent)
    }
    
    private fun setupRecentPractitioners() {
        val recentPractitioners = getRecentPractitioners()
        displayRecentPractitioners(recentPractitioners)
    }
    
    private fun getRecentPractitioners(): List<RecentPractitioner> {
        val json = sharedPreferences.getString("practitioners_list", "[]") ?: "[]"
        val type = object : TypeToken<List<RecentPractitioner>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    
    private fun saveRecentPractitioner(practitioner: RecentPractitioner) {
        val currentList = getRecentPractitioners().toMutableList()
        
        // 既存の同じIDのエスティシャンを削除
        currentList.removeAll { it.id == practitioner.id }
        
        // 新しいエスティシャンを先頭に追加
        currentList.add(0, practitioner)
        
        // 最大5人まで制限
        val limitedList = currentList.take(maxRecentPractitioners)
        
        // SharedPreferencesに保存
        val json = gson.toJson(limitedList)
        sharedPreferences.edit().putString("practitioners_list", json).apply()
        
        // UIを更新
        displayRecentPractitioners(limitedList)
    }
    
    private fun displayRecentPractitioners(practitioners: List<RecentPractitioner>) {
        val practitionerLayouts = listOf(
            findViewById<LinearLayout>(R.id.recent_practitioner_1),
            findViewById<LinearLayout>(R.id.recent_practitioner_2),
            findViewById<LinearLayout>(R.id.recent_practitioner_3),
            findViewById<LinearLayout>(R.id.recent_practitioner_4),
            findViewById<LinearLayout>(R.id.recent_practitioner_5)
        )
        
        val practitionerImages = listOf(
            findViewById<ImageView>(R.id.practitioner_image_1),
            findViewById<ImageView>(R.id.practitioner_image_2),
            findViewById<ImageView>(R.id.practitioner_image_3),
            findViewById<ImageView>(R.id.practitioner_image_4),
            findViewById<ImageView>(R.id.practitioner_image_5)
        )
        
        val practitionerNames = listOf(
            findViewById<TextView>(R.id.practitioner_name_1),
            findViewById<TextView>(R.id.practitioner_name_2),
            findViewById<TextView>(R.id.practitioner_name_3),
            findViewById<TextView>(R.id.practitioner_name_4),
            findViewById<TextView>(R.id.practitioner_name_5)
        )
        
        // 初期状態で全ての施術者レイアウトを非表示にする
        practitionerLayouts.forEach { it.visibility = LinearLayout.GONE }
        
        // 最近チェックした施術者のデータを表示
        practitioners.forEachIndexed { index, practitioner ->
            if (index < practitionerLayouts.size) {
                practitionerLayouts[index].visibility = LinearLayout.VISIBLE
                practitionerNames[index].text = practitioner.name
                
                // プレースホルダー画像を設定（実際の画像読み込みはここで実装）
                // TODO: 実際の画像URLから画像を読み込む処理を追加
                practitionerImages[index].setImageResource(R.color.background_card)
                
                // クリックリスナーを設定
                practitionerLayouts[index].setOnClickListener {
                    val intent = Intent(this, PractitionerDetailActivity::class.java)
                    intent.putExtra("practitioner_id", practitioner.id)
                    intent.putExtra("practitioner_name", practitioner.name)
                    startActivity(intent)
                }
            }
        }
        
        // デフォルトのサンプルデータを表示（実際のデータがない場合）
        if (practitioners.isEmpty()) {
            displaySamplePractitioners()
        }
    }
    
    private fun displaySamplePractitioners() {
        val samplePractitioners = listOf(
            RecentPractitioner("1", "Emma", null),
            RecentPractitioner("2", "Maya", null),
            RecentPractitioner("3", "Sofia", null)
        )
        
        samplePractitioners.forEachIndexed { index, practitioner ->
            saveRecentPractitioner(practitioner)
        }
    }
    
    // 他のアクティビティから呼び出すためのpublicメソッド
    companion object {
        fun addRecentPractitioner(context: TopActivity, practitioner: RecentPractitioner) {
            context.saveRecentPractitioner(practitioner)
        }
    }

}