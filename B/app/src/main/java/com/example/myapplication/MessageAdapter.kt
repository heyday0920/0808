package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val messages: List<Message>) : 
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.profile_image)
        val senderName: TextView = itemView.findViewById(R.id.sender_name)
        val messagePreview: TextView = itemView.findViewById(R.id.message_preview)
        val unreadBadge: LinearLayout = itemView.findViewById(R.id.unread_badge)
        val unreadCount: TextView = itemView.findViewById(R.id.unread_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        
        holder.senderName.text = message.senderName
        holder.messagePreview.text = message.messagePreview
        
        // プロフィール画像を設定
        holder.profileImage.setImageResource(message.profileImage)
        
        // 未読バッジの表示/非表示を設定
        if (message.unreadCount > 0) {
            holder.unreadBadge.visibility = View.VISIBLE
            holder.unreadCount.text = message.unreadCount.toString()
        } else {
            holder.unreadBadge.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = messages.size
} 