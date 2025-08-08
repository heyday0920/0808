package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PractitionerDetailActivity : AppCompatActivity() {
    
    private lateinit var practitionerImage: ImageView
    private lateinit var practitionerName: TextView
    private lateinit var practitionerRating: TextView
    private lateinit var chatButton: LinearLayout
    private lateinit var reservationButton: Button
    private lateinit var planCard: LinearLayout
    
    // プラン展開機能用
    private var isPlanDetailVisible = false
    
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private val maxRecentPractitioners = 5
    
    // Tab buttons
    private lateinit var tabProfile: TextView
    private lateinit var tabStoreInfo: TextView
    
    // Gallery tabs
    private lateinit var galleryPlan: Button
    private lateinit var galleryReview: Button
    private lateinit var galleryBeforeAfter: Button
    private lateinit var galleryProfile: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("PractitionerDetailActivity", "onCreate called")
        
        setContentView(R.layout.activity_practitioner_detail)
        
        sharedPreferences = getSharedPreferences("recent_practitioners", MODE_PRIVATE)
        
        initializeViews()
        setupClickListeners()
        loadPractitionerData()
        setupBottomNavigation()
        setupHeaderMenu()
        
        // 初期状態を設定（プロフィールタブをアクティブに）
        showProfileContent()
        
        // 履歴に追加
        addToRecentPractitioners()
    }
    
    private fun initializeViews() {
        practitionerImage = findViewById(R.id.practitionerImage)
        practitionerName = findViewById(R.id.practitionerName)
        practitionerRating = findViewById(R.id.practitionerRating)
        chatButton = findViewById(R.id.chatButton)
        reservationButton = findViewById(R.id.reservationButton)
        planCard = findViewById(R.id.planCard)
        
        // Tabs
        tabProfile = findViewById(R.id.tab_profile)
        tabStoreInfo = findViewById(R.id.tab_store_info)
        
        // Gallery tabs
        galleryPlan = findViewById(R.id.gallery_plan)
        galleryReview = findViewById(R.id.gallery_review)
        galleryBeforeAfter = findViewById(R.id.gallery_before_after)
        galleryProfile = findViewById(R.id.gallery_profile)
    }
    
    private fun setupClickListeners() {
        // Main tabs
        tabProfile.setOnClickListener {
            switchToProfile()
        }
        
        tabStoreInfo.setOnClickListener {
            switchToStoreInfo()
        }
        
        // Chat button
        chatButton.setOnClickListener {
            Toast.makeText(this, "チャット機能を開きます", Toast.LENGTH_SHORT).show()
        }
        
        // Reservation button
        reservationButton.setOnClickListener {
            // Switch to plan tab when reservation button is clicked
            switchGalleryTab("プラン")
            Toast.makeText(this, "プランタブに切り替えました", Toast.LENGTH_SHORT).show()
        }
        
        // Plan card（展開/折りたたみ）
        planCard.setOnClickListener {
            Log.d("PractitionerDetailActivity", "planCard clicked! Current visibility: $isPlanDetailVisible")
            togglePlanDetail()
        }
        
        // Gallery tabs
        galleryPlan.setOnClickListener {
            switchGalleryTab("プラン")
        }
        
        galleryReview.setOnClickListener {
            switchGalleryTab("レビュー")
        }
        
        galleryBeforeAfter.setOnClickListener {
            switchGalleryTab("ビフォーアフター")
        }
        
        galleryProfile.setOnClickListener {
            switchGalleryTab("プロフィール")
        }
    }
    
    private fun loadPractitionerData() {
        // Load practitioner data (for now using static data)
        practitionerName.text = "Emma myers(リラク 新宿東横店内)"
        practitionerRating.text = " 4.0"
        
        android.util.Log.d("PractitionerDetailActivity", "Practitioner data loaded")
    }
    
    private fun switchToProfile() {
        // Update tab appearance
        tabProfile.setBackgroundColor(getColor(R.color.primary_purple_light))
        tabProfile.setTextColor(getColor(R.color.text_primary))
        
        tabStoreInfo.setBackgroundColor(getColor(R.color.background_white))
        tabStoreInfo.setTextColor(getColor(R.color.text_secondary))
        
        // Show profile content
        showProfileContent()
        
        Toast.makeText(this, "プロフィールに切り替えました", Toast.LENGTH_SHORT).show()
    }
    
    private fun switchToStoreInfo() {
        // Update tab appearance
        tabProfile.setBackgroundColor(getColor(R.color.background_white))
        tabProfile.setTextColor(getColor(R.color.text_secondary))
        
        tabStoreInfo.setBackgroundColor(getColor(R.color.primary_purple_light))
        tabStoreInfo.setTextColor(getColor(R.color.text_primary))
        
        // Hide profile content and show store content
        hideProfileContent()
        
        Toast.makeText(this, "店舗情報に切り替えました", Toast.LENGTH_SHORT).show()
    }
    
    private fun switchGalleryTab(tabName: String) {
        // Reset all gallery tabs
        resetGalleryTabs()
        
        // Set selected tab
        when (tabName) {
            "プラン" -> {
                galleryPlan.setBackgroundResource(R.drawable.bg_button_primary)
                galleryPlan.setTextColor(getColor(R.color.white))
                showDefaultContent()
            }
            "レビュー" -> {
                galleryReview.setBackgroundResource(R.drawable.bg_button_primary)
                galleryReview.setTextColor(getColor(R.color.white))
                showReviewContent()
            }
            "ビフォーアフター" -> {
                galleryBeforeAfter.setBackgroundResource(R.drawable.bg_button_primary)
                galleryBeforeAfter.setTextColor(getColor(R.color.white))
                showDefaultContent()
            }
            "プロフィール" -> {
                galleryProfile.setBackgroundResource(R.drawable.bg_button_primary)
                galleryProfile.setTextColor(getColor(R.color.white))
                showDefaultContent()
            }
        }
        
        Toast.makeText(this, "${tabName}に切り替えました", Toast.LENGTH_SHORT).show()
    }
    
    private fun resetGalleryTabs() {
        val tabs = listOf(galleryPlan, galleryReview, galleryBeforeAfter, galleryProfile)
        tabs.forEach { tab ->
            tab.setBackgroundResource(R.drawable.tag_background)
            tab.setTextColor(getColor(R.color.text_secondary))
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
    
    private fun setupHeaderMenu() {
        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish() // 前のページに戻る
        }
    }
    
    private fun togglePlanDetail() {
        val planDetailContainer = findViewById<LinearLayout>(R.id.planDetailContainer)
        isPlanDetailVisible = !isPlanDetailVisible
        
        Log.d("PractitionerDetailActivity", "togglePlanDetail: visibility changed to $isPlanDetailVisible")
        
        // 元のプランカードと詳細コンテナの表示を切り替え
        planCard.visibility = if (isPlanDetailVisible) View.GONE else View.VISIBLE
        planDetailContainer.visibility = if (isPlanDetailVisible) View.VISIBLE else View.GONE
        
        // 詳細が表示された後に予約ボタンのリスナーを設定
        if (isPlanDetailVisible) {
            val menuReservationButton = findViewById<Button>(R.id.menuReservationButton)
            menuReservationButton.setOnClickListener {
                val intent = Intent(this, DateTimeSelectionActivity::class.java)
                intent.putExtra("courseName", "クイックボディケア")
                intent.putExtra("courseDuration", "30分")
                intent.putExtra("coursePrice", "￥3,300")
                startActivity(intent)
            }
            
            // メニュー詳細ボタンで元の表示に戻る
            val menuDetailButton = findViewById<Button>(R.id.menuDetailButton)
            menuDetailButton.setOnClickListener {
                isPlanDetailVisible = false
                planCard.visibility = View.VISIBLE
                planDetailContainer.visibility = View.GONE
                Toast.makeText(this, "プラン一覧に戻りました", Toast.LENGTH_SHORT).show()
            }
        }
        
        Toast.makeText(this, if (isPlanDetailVisible) "プラン詳細を表示しました" else "プラン一覧を表示しました", Toast.LENGTH_SHORT).show()
    }
    
    private fun addToRecentPractitioners() {
        // デフォルトのエスティシャン情報または Intentから受け取った情報を使用
        val practitionerId = intent.getStringExtra("practitioner_id") ?: "emma_myers"
        val practitionerNameText = intent.getStringExtra("practitioner_name") ?: "Emma myers(リラク 新宿東横店内)"
        
        val practitioner = RecentPractitioner(
            id = practitionerId,
            name = extractFirstName(practitionerNameText),
            imageUrl = null // 実際の画像URLがあれば設定
        )
        
        saveRecentPractitioner(practitioner)
    }
    
    private fun extractFirstName(fullName: String): String {
        // フルネームから名前の部分を抽出
        return when {
            fullName.contains("Emma") -> "Emma"
            fullName.contains("Maya") -> "Maya"
            fullName.contains("Sofia") -> "Sofia"
            fullName.contains("Lisa") -> "Lisa"
            fullName.contains("Anna") -> "Anna"
            else -> fullName.split("(")[0].trim()
        }
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
        
        android.util.Log.d("PractitionerDetailActivity", "Added to recent: ${practitioner.name}")
    }
    
    private fun getRecentPractitioners(): List<RecentPractitioner> {
        val json = sharedPreferences.getString("practitioners_list", "[]") ?: "[]"
        val type = object : TypeToken<List<RecentPractitioner>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    
    private fun showProfileContent() {
        // プロフィールタブのコンテンツを表示
        findViewById<LinearLayout>(R.id.gallery_tabs_section)?.visibility = LinearLayout.VISIBLE
        findViewById<LinearLayout>(R.id.image_gallery_section)?.visibility = LinearLayout.VISIBLE
        findViewById<LinearLayout>(R.id.reservation_section)?.visibility = LinearLayout.VISIBLE
        findViewById<LinearLayout>(R.id.treatment_plans_section)?.visibility = LinearLayout.VISIBLE
    }
    
    private fun hideProfileContent() {
        // プロフィールタブのコンテンツを非表示
        findViewById<LinearLayout>(R.id.gallery_tabs_section)?.visibility = LinearLayout.GONE
        findViewById<LinearLayout>(R.id.image_gallery_section)?.visibility = LinearLayout.GONE
        findViewById<LinearLayout>(R.id.reservation_section)?.visibility = LinearLayout.GONE
        findViewById<LinearLayout>(R.id.treatment_plans_section)?.visibility = LinearLayout.GONE
    }
    
    private fun showDefaultContent() {
        // デフォルトコンテンツを表示（画像ギャラリー、予約ボタン、施術プラン）
        findViewById<LinearLayout>(R.id.image_gallery_section)?.visibility = LinearLayout.VISIBLE
        findViewById<LinearLayout>(R.id.reservation_section)?.visibility = LinearLayout.VISIBLE
        findViewById<LinearLayout>(R.id.treatment_plans_section)?.visibility = LinearLayout.VISIBLE
        
        // レビューコンテンツを非表示
        hideReviewContent()
    }
    
    private fun showReviewContent() {
        // デフォルトコンテンツを非表示
        findViewById<LinearLayout>(R.id.image_gallery_section)?.visibility = LinearLayout.GONE
        findViewById<LinearLayout>(R.id.reservation_section)?.visibility = LinearLayout.GONE
        findViewById<LinearLayout>(R.id.treatment_plans_section)?.visibility = LinearLayout.GONE
        
        // レビューコンテンツを表示
        setupReviewRecyclerView()
    }
    
    private fun hideReviewContent() {
        // レビューRecyclerViewを非表示（存在する場合）
        val mainContent = findViewById<LinearLayout>(R.id.treatment_plans_section)?.parent as? LinearLayout
        val reviewRecyclerView = mainContent?.findViewById<RecyclerView>(R.id.review_recycler_view)
        reviewRecyclerView?.visibility = RecyclerView.GONE
    }
    
    private fun setupReviewRecyclerView() {
        val mainContent = findViewById<LinearLayout>(R.id.treatment_plans_section)?.parent as? LinearLayout
        
        // 既存のレビューRecyclerViewを探す
        var reviewRecyclerView = mainContent?.findViewById<RecyclerView>(R.id.review_recycler_view)
        
        // レビューRecyclerViewが存在しない場合は作成
        if (reviewRecyclerView == null && mainContent != null) {
            reviewRecyclerView = RecyclerView(this).apply {
                id = R.id.review_recycler_view
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 16, 0, 0)
                }
                layoutManager = LinearLayoutManager(this@PractitionerDetailActivity)
            }
            mainContent.addView(reviewRecyclerView)
        }
        
        // レビューデータを設定
        reviewRecyclerView?.let { recyclerView ->
            recyclerView.visibility = RecyclerView.VISIBLE
            val reviews = createSampleReviews()
            val adapter = ReviewAdapter(reviews)
            recyclerView.adapter = adapter
        }
    }
    
    private fun createSampleReviews(): List<Review> {
        return listOf(
            Review(
                id = 1,
                userImage = null,
                userName = "大阪府内",
                date = "2024年1月15日",
                overallRating = 4.0f,
                skillRating = 4.0f,
                hospitilityRating = 4.0f,
                responseRating = 4.0f,
                cleanlinessRating = 4.0f,
                location = "大阪府内",
                planName = "リフレッシュ全身マッサージ 自身おちにリラッシュしたくて",
                price = "¥9,000",
                reviewText = "技術や雰囲気で満足していただけて、リフレッシュして帰ることができるエステを利用しました。\n\n清潔感のある開店に好感を持てました。スタッフの方もとても丁寧でした。\n\nよく、新たに清潔感のあるサロンへ通われて店内で過ごす時間を利点だろうとマッサージして帰ることができましたこの運営まで、スタッフの皆様の肌触りが心地よくまでライブ料金を続けてよかったです。\n\nレディ、お客様へ過ごしからリフレッシュできるエステです。",
                reviewImages = listOf("", "", "")
            ),
            Review(
                id = 2,
                userImage = null,
                userName = "東京都内",
                date = "2024年1月10日",
                overallRating = 5.0f,
                skillRating = 5.0f,
                hospitilityRating = 5.0f,
                responseRating = 4.0f,
                cleanlinessRating = 5.0f,
                location = "東京都内",
                planName = "フェイシャルエステ 美肌コース",
                price = "¥7,500",
                reviewText = "初めてのエステでしたが、とても満足できました。施術前のカウンセリングも丁寧で、自分の肌の状態に合わせたケアをしていただけました。施術後は肌がもちもちになり、化粧のりも良くなりました。また利用したいと思います。",
                reviewImages = listOf("", "", "")
            )
        )
    }
} 