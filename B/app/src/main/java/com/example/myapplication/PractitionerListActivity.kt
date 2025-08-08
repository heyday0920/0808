package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.net.ConnectivityManager
import android.content.Context

class PractitionerListActivity : AppCompatActivity() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var practitionerAdapter: PractitionerAdapter
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var storeRepository: StoreRepository
    private lateinit var practitionerRepository: PractitionerRepository
    
    private lateinit var tabPractitioner: TextView
    private lateinit var tabStore: TextView
    
    private var currentTab = "practitioner" // "practitioner" or "store"
    private var selectedCategory = "エステ" // Default category
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("PractitionerListActivity", "onCreate called")
        
        setContentView(R.layout.activity_practitioner_list)
        
        // Get category from intent
        selectedCategory = intent.getStringExtra("category") ?: "エステ"
        android.util.Log.d("PractitionerListActivity", "Category: $selectedCategory")
        
        storeRepository = StoreRepository()
        practitionerRepository = PractitionerRepository()
        setupViews()
        setupRecyclerView()
        setupTabs()
        setupBottomNavigation()
        setupHeaderMenu()
    }
    
    private fun setupViews() {
        tabPractitioner = findViewById(R.id.tab_practitioner)
        tabStore = findViewById(R.id.tab_store)
    }
    
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.practitionerRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Initialize adapters with empty lists first
        practitionerAdapter = PractitionerAdapter(emptyList())
        storeAdapter = StoreAdapter(emptyList())
        
        // Load data from Firebase
        loadPractitionersFromFirebase()
        loadStoresFromFirebase()
        
        // Initially show practitioners
        recyclerView.adapter = practitionerAdapter
        
        android.util.Log.d("PractitionerListActivity", "RecyclerView setup completed")
    }
    
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun loadPractitionersFromFirebase() {
        android.util.Log.d("PractitionerListActivity", "Loading practitioners for category: $selectedCategory")
        
        // Check network connectivity
        if (!isNetworkAvailable()) {
            android.util.Log.w("PractitionerListActivity", "No network connection available")
            Toast.makeText(this, "ネットワーク接続を確認してください", Toast.LENGTH_LONG).show()
        }
        
        practitionerRepository.getPractitionersByCategory(
            category = selectedCategory,
            onSuccess = { practitioners ->
                android.util.Log.d("PractitionerListActivity", "Loaded ${practitioners.size} practitioners from Firebase for category: $selectedCategory")
                
                // Log practitioner names and images for testing
                practitioners.forEach { practitioner ->
                    android.util.Log.d("PractitionerListActivity", "=== PRACTITIONER DATA ===")
                    android.util.Log.d("PractitionerListActivity", "Name: ${practitioner.name}")
                    android.util.Log.d("PractitionerListActivity", "Tag: ${practitioner.tag}")
                    android.util.Log.d("PractitionerListActivity", "Tag2: ${practitioner.tag2}")
                    android.util.Log.d("PractitionerListActivity", "Category: ${practitioner.category}")
                    android.util.Log.d("PractitionerListActivity", "ProfileImage: ${practitioner.imageUrl}")
                    android.util.Log.d("PractitionerListActivity", "CourseImage1: ${practitioner.course_image1}")
                    android.util.Log.d("PractitionerListActivity", "CourseImage2: ${practitioner.course_image2}")
                    android.util.Log.d("PractitionerListActivity", "CourseImage3: ${practitioner.course_image3}")
                    android.util.Log.d("PractitionerListActivity", "CourseImage4: ${practitioner.course_image4}")
                    android.util.Log.d("PractitionerListActivity", "CourseImage5: ${practitioner.course_image5}")
                    android.util.Log.d("PractitionerListActivity", "========================")
                }
                
                practitionerAdapter = PractitionerAdapter(practitioners)
                
                // If currently showing practitioners, update the adapter
                if (currentTab == "practitioner") {
                    recyclerView.adapter = practitionerAdapter
                }
                
                // Show success message with count
                Toast.makeText(this, "${selectedCategory}の施術者を${practitioners.size}件読み込みました", Toast.LENGTH_SHORT).show()
            },
            onError = { exception ->
                android.util.Log.e("PractitionerListActivity", "Error loading practitioners: ${exception.message}")
                android.util.Log.e("PractitionerListActivity", "Exception type: ${exception.javaClass.simpleName}")
                exception.printStackTrace()
                
                Toast.makeText(this, "施術者データの読み込みに失敗しました: ${exception.message}", Toast.LENGTH_LONG).show()
                
                // Fallback to sample data with matching category
                val samplePractitioners = createSamplePractitioners().filter { it.category == selectedCategory || it.category.isEmpty() }
                android.util.Log.d("PractitionerListActivity", "Using ${samplePractitioners.size} sample practitioners as fallback")
                practitionerAdapter = PractitionerAdapter(samplePractitioners)
                
                if (currentTab == "practitioner") {
                    recyclerView.adapter = practitionerAdapter
                }
            }
        )
    }
    
    private fun loadStoresFromFirebase() {
        android.util.Log.d("PractitionerListActivity", "Loading stores for category: $selectedCategory")
        
        storeRepository.getStoresByCategory(
            category = selectedCategory,
            onSuccess = { stores ->
                android.util.Log.d("PractitionerListActivity", "Loaded ${stores.size} stores from Firebase for category: $selectedCategory")
                
                // Log store names for testing
                stores.forEach { store ->
                    android.util.Log.d("PractitionerListActivity", "Store found: ${store.name} (Category: ${store.category})")
                }
                
                storeAdapter = StoreAdapter(stores)
                
                // If currently showing stores, update the adapter
                if (currentTab == "store") {
                    recyclerView.adapter = storeAdapter
                }
                
                // Show success message with count
                Toast.makeText(this, "${selectedCategory}の店舗を${stores.size}件読み込みました", Toast.LENGTH_SHORT).show()
            },
            onError = { exception ->
                android.util.Log.e("PractitionerListActivity", "Error loading stores: ${exception.message}")
                android.util.Log.e("PractitionerListActivity", "Exception type: ${exception.javaClass.simpleName}")
                exception.printStackTrace()
                
                Toast.makeText(this, "店舗データの読み込みに失敗しました: ${exception.message}", Toast.LENGTH_LONG).show()
                
                // Fallback to sample data with matching category
                val sampleStores = createSampleStores().filter { it.category == selectedCategory || it.category.isEmpty() }
                android.util.Log.d("PractitionerListActivity", "Using ${sampleStores.size} sample stores as fallback")
                storeAdapter = StoreAdapter(sampleStores)
                
                if (currentTab == "store") {
                    recyclerView.adapter = storeAdapter
                }
            }
        )
    }
    
    private fun setupTabs() {
        tabPractitioner.setOnClickListener {
            switchTab("practitioner")
        }
        
        tabStore.setOnClickListener {
            switchTab("store")
        }
    }
    
    private fun createSamplePractitioners(): List<Practitioner> {
        return listOf(
            Practitioner(
                id = "sample1",
                name = "Emma myers",
                tag = "認定エステティシャン",
                tag2 = "日本エステティック協会認定",
                rating = 4.0,
                reviewCount = 403,
                nearest = "",
                plan = "ラグジュアリーフェイスエステ 60分",
                review = "技術も高く丁寧な施術をしていただきました。リラックスもでき大変良い時間エステを楽しむことしました。清潔感のあるお部屋に好感を持てました。スタッフの方もとても優しかったです。・・・",
                price = "8900円",
                category = "エステ",
                storeName = "リラク 新宿東横店内",
                imageUrl = "https://picsum.photos/200/200",
                course_image1 = "https://picsum.photos/300/200?random=1",
                course_image2 = "https://picsum.photos/300/200?random=2",
                course_image3 = "https://picsum.photos/300/200?random=3",
                course_image4 = "https://picsum.photos/300/200?random=4",
                course_image5 = "https://picsum.photos/300/200?random=5"
            ),
            Practitioner(
                id = "sample2",
                name = "田中花子",
                tag = "認定エステティシャン",
                tag2 = "", // 2つ目の資格なし
                rating = 4.0,
                reviewCount = 250,
                nearest = "",
                plan = "リラクゼーションフェイス 45分",
                review = "とても丁寧な施術で満足できました。リラックスした時間を過ごすことができ、肌の調子も良くなりました。スタッフの技術力も高く、また利用したいと思います。",
                price = "6500円",
                category = "エステ",
                storeName = "ビューティーサロン東京"
            ),
            Practitioner(
                id = "sample3",
                name = "Sofia kim",
                tag = "認定エステティシャン",
                tag2 = "AEA認定",
                rating = 4.0,
                reviewCount = 189,
                nearest = "",
                plan = "プレミアムフェイシャル 90分",
                review = "プレミアムコースは値段相応の価値がありました。90分という時間でじっくりとケアしていただき、肌がワントーン明るくなった気がします。",
                price = "12000円",
                category = "エステ",
                storeName = "エステティックスパ銀座"
            )
        )
    }
    
    private fun setupBottomNavigation() {
        val bottomSearch = findViewById<LinearLayout>(R.id.bottom_search)
        val bottomHome = findViewById<LinearLayout>(R.id.bottom_home)
        val bottomChat = findViewById<LinearLayout>(R.id.bottom_chat)
        val bottomProfile = findViewById<LinearLayout>(R.id.bottom_profile)
        
        val navigationHelper = BottomNavigationHelper(this)
        navigationHelper.setupBottomNavigation(
            bottomSearch, bottomHome, bottomChat, bottomProfile, "search"
        )
    }
    
    private fun setupHeaderMenu() {
        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish() // 前のページに戻る
        }
    }
    
    private fun switchTab(tab: String) {
        currentTab = tab
        
        when (tab) {
            "practitioner" -> {
                // Update tab appearance
                tabPractitioner.setBackgroundColor(getColor(R.color.primary_purple_light))
                tabPractitioner.setTextColor(getColor(R.color.text_primary))
                tabStore.setBackgroundColor(getColor(R.color.background_white))
                tabStore.setTextColor(getColor(R.color.text_secondary))
                
                // Switch to practitioner adapter
                recyclerView.adapter = practitionerAdapter
                
                Toast.makeText(this, "施術者一覧に切り替えました", Toast.LENGTH_SHORT).show()
            }
            "store" -> {
                // Update tab appearance
                tabStore.setBackgroundColor(getColor(R.color.primary_purple_light))
                tabStore.setTextColor(getColor(R.color.text_primary))
                tabPractitioner.setBackgroundColor(getColor(R.color.background_white))
                tabPractitioner.setTextColor(getColor(R.color.text_secondary))
                
                // Switch to store adapter
                recyclerView.adapter = storeAdapter
                
                Toast.makeText(this, "店舗一覧に切り替えました", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun createSampleStores(): List<Store> {
        return listOf(
            Store(
                id = "sample1",
                name = "リラク 新宿東横店内",
                description = "",
                rating = 4.0,
                reviewCount = 403,
                images = listOf("", "", ""),
                planName = "ラグジュアリーフェイスエステ 60分",
                planPrice = "8900円",
                ownerName = "Emma myers(リラク 新宿東横店内)",
                location = "新宿",
                reviewText = "技術や雰囲気で満足していただけで、リフレッシュして帰ることができるエステを利用しました。清潔感のある開店に好感を持てました。スタッフの方もとても親切でした。・・・",
                reviewCategory = "全身のセルルバー毒素",
                category = "エステ",
                access = "新宿駅徒歩5分",
                address = "東京都新宿区",
                station = "新宿駅"
            ),
            Store(
                id = "sample2",
                name = "ビューティーサロン東京",
                description = "",
                rating = 4.0,
                reviewCount = 250,
                images = listOf("", "", ""),
                planName = "リラクゼーションフェイス 45分",
                planPrice = "6500円",
                ownerName = "Maya tanaka(ビューティーサロン東京)",
                location = "渋谷",
                reviewText = "とても丁寧な施術で満足できました。リラックスした時間を過ごすことができ、肌の調子も良くなりました。スタッフの技術力も高く、また利用したいと思います。",
                reviewCategory = "フェイシャルケア",
                category = "エステ",
                access = "渋谷駅徒歩3分",
                address = "東京都渋谷区",
                station = "渋谷駅"
            ),
            Store(
                id = "sample3",
                name = "エステティックスパ銀座",
                description = "",
                rating = 4.0,
                reviewCount = 189,
                images = listOf("", "", ""),
                planName = "プレミアムフェイシャル 90分",
                planPrice = "12000円",
                ownerName = "Sofia kim(エステティックスパ銀座)",
                location = "銀座",
                reviewText = "プレミアムコースは値段相応の価値がありました。90分という時間でじっくりとケアしていただき、肌がワントーン明るくなった気がします。",
                reviewCategory = "プレミアムケア",
                category = "エステ",
                access = "銀座駅徒歩2分",
                address = "東京都中央区銀座",
                station = "銀座駅"
            )
        )
    }
} 