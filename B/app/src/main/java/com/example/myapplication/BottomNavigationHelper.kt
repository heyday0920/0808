package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast

class BottomNavigationHelper(private val context: Context) {
    
    fun setupBottomNavigation(
        bottomSearch: LinearLayout,
        bottomHome: LinearLayout,
        bottomChat: LinearLayout,
        bottomProfile: LinearLayout,
        currentActivity: String = ""
    ) {
        
        // 現在のアクティビティに応じて選択状態を設定
        updateSelectionState(bottomSearch, bottomHome, bottomChat, bottomProfile, currentActivity)
        
        // 検索ボタン
        bottomSearch.setOnClickListener {
            if (currentActivity != "search") {
                // 検索画面（TOP画面）に遷移
                val intent = Intent(context, TopActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                context.startActivity(intent)
            }
        }
        
        // ホームボタン
        bottomHome.setOnClickListener {
            if (currentActivity != "home") {
                // ホーム画面に遷移
                val intent = Intent(context, TopActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                context.startActivity(intent)
            }
        }
        
        // チャットボタン
        bottomChat.setOnClickListener {
            if (currentActivity != "chat") {
                // メッセージ画面に遷移
                val intent = Intent(context, MessageActivity::class.java)
                context.startActivity(intent)
            }
        }
        
        // プロフィールボタン
        bottomProfile.setOnClickListener {
            if (currentActivity != "profile") {
                // プロフィール画面に遷移
                Toast.makeText(context, "プロフィール画面を開きます", Toast.LENGTH_SHORT).show()
                // Intent intent = new Intent(context, ProfileActivity.class);
                // context.startActivity(intent);
            }
        }
    }
    
    private fun updateSelectionState(
        bottomSearch: LinearLayout,
        bottomHome: LinearLayout,
        bottomChat: LinearLayout,
        bottomProfile: LinearLayout,
        currentActivity: String
    ) {
        // すべてのボタンを非選択状態にリセット
        resetAllButtons(bottomSearch, bottomHome, bottomChat, bottomProfile)
        
        // 現在のアクティビティに応じて選択状態を設定
        when (currentActivity) {
            "search" -> selectButton(bottomSearch)
            "home" -> selectButton(bottomHome)
            "chat" -> selectButton(bottomChat)
            "profile" -> selectButton(bottomProfile)
            else -> selectButton(bottomHome) // デフォルトはホーム
        }
    }
    
    private fun resetAllButtons(
        bottomSearch: LinearLayout,
        bottomHome: LinearLayout,
        bottomChat: LinearLayout,
        bottomProfile: LinearLayout
    ) {
        val buttons = listOf(bottomSearch, bottomHome, bottomChat, bottomProfile)
        buttons.forEach { button ->
            val imageView = button.getChildAt(0) as ImageView
            val textView = button.getChildAt(1) as TextView
            
            imageView.setColorFilter(context.getColor(R.color.text_secondary))
            textView.setTextColor(context.getColor(R.color.text_secondary))
        }
    }
    
    private fun selectButton(button: LinearLayout) {
        val imageView = button.getChildAt(0) as ImageView
        val textView = button.getChildAt(1) as TextView
        
        imageView.setColorFilter(context.getColor(R.color.primary_purple))
        textView.setTextColor(context.getColor(R.color.primary_purple))
    }
} 