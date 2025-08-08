package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessageActivity : AppCompatActivity() {
    
    private lateinit var messagesTab: TextView
    private lateinit var calendarTab: TextView
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var calendarRecyclerView: RecyclerView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        
        initializeViews()
        setupTabs()
        setupMessageList()
        setupHeader()
        setupFooterNavigation()
    }
    
    private fun initializeViews() {
        try {
            messagesTab = findViewById(R.id.messages_tab)
            calendarTab = findViewById(R.id.calendar_tab)
            messagesRecyclerView = findViewById(R.id.messages_recycler_view)
            calendarRecyclerView = findViewById(R.id.calendar_recycler_view)
        } catch (e: Exception) {
            android.util.Log.e("MessageActivity", "Error initializing views: ${e.message}")
        }
    }
    
    private fun setupTabs() {
        // 初期状態でメッセージタブをアクティブにする
        setActiveTab("messages")
        
        messagesTab.setOnClickListener {
            setActiveTab("messages")
        }
        
        calendarTab.setOnClickListener {
            setActiveTab("calendar")
        }
    }
    
    private fun setActiveTab(activeTab: String) {
        when (activeTab) {
            "messages" -> {
                messagesTab.setTextColor(getColor(R.color.primary_purple))
                calendarTab.setTextColor(getColor(R.color.text_primary))
                messagesRecyclerView.visibility = View.VISIBLE
                calendarRecyclerView.visibility = View.GONE
            }
            "calendar" -> {
                messagesTab.setTextColor(getColor(R.color.text_primary))
                calendarTab.setTextColor(getColor(R.color.primary_purple))
                messagesRecyclerView.visibility = View.GONE
                calendarRecyclerView.visibility = View.VISIBLE
                
                // カレンダー画面のレイアウトを設定
                setContentView(R.layout.activity_calendar)
                // レイアウト変更後にViewを再初期化
                initializeViews()
                setupCalendarHeader()
                setupCalendarFooterNavigation()
            }
        }
    }
    
    private fun setupMessageList() {
        val messages = listOf(
            Message(
                senderName = "山田孝之(リラク 新宿)",
                messagePreview = "予約ありがとうございます!",
                unreadCount = 1,
                profileImage = R.color.background_card
            )
        )
        
        val adapter = MessageAdapter(messages)
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        messagesRecyclerView.adapter = adapter
    }
    
    private fun setupHeader() {
        try {
            val backButton = findViewById<ImageView>(R.id.back_button)
            backButton?.setOnClickListener {
                finish() // 前のページに戻る
            }
        } catch (e: Exception) {
            android.util.Log.e("MessageActivity", "Error setting up header menu: ${e.message}")
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
                bottomSearch, bottomHome, bottomChat, bottomProfile, "chat"
            )
        } catch (e: Exception) {
            android.util.Log.e("MessageActivity", "Error setting up footer navigation: ${e.message}")
        }
    }
    
    private fun setupCalendarHeader() {
        try {
            val backButton = findViewById<ImageView>(R.id.back_button)
            backButton?.setOnClickListener {
                finish() // 前のページに戻る
            }
        } catch (e: Exception) {
            android.util.Log.e("MessageActivity", "Error setting up calendar header: ${e.message}")
        }
    }
    
    private fun setupCalendarFooterNavigation() {
        try {
            val bottomSearch = findViewById<LinearLayout>(R.id.bottom_search)
            val bottomHome = findViewById<LinearLayout>(R.id.bottom_home)
            val bottomChat = findViewById<LinearLayout>(R.id.bottom_chat)
            val bottomProfile = findViewById<LinearLayout>(R.id.bottom_profile)
            
            val navigationHelper = BottomNavigationHelper(this)
            navigationHelper.setupBottomNavigation(
                bottomSearch, bottomHome, bottomChat, bottomProfile, "chat"
            )
        } catch (e: Exception) {
            android.util.Log.e("MessageActivity", "Error setting up calendar footer navigation: ${e.message}")
        }
    }
}

data class Message(
    val senderName: String,
    val messagePreview: String,
    val unreadCount: Int,
    val profileImage: Int
) 