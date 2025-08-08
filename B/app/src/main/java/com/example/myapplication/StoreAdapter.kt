package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoreAdapter(private val stores: List<Store>) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storeCard: LinearLayout = itemView.findViewById(R.id.store_card)
        val storeName: TextView = itemView.findViewById(R.id.store_name)
        val storeAccess: TextView = itemView.findViewById(R.id.store_access)
        val storeRating: TextView = itemView.findViewById(R.id.store_rating)
        val storeImages: LinearLayout = itemView.findViewById(R.id.store_images)
        val planName: TextView = itemView.findViewById(R.id.plan_name)
        val planPrice: TextView = itemView.findViewById(R.id.plan_price)
        val reviewText: TextView = itemView.findViewById(R.id.review_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = stores[position]
        
        holder.storeName.text = store.name
        holder.storeAccess.text = store.access
        holder.storeRating.text = " ${store.rating} (${store.reviewCount})"
        holder.planName.text = store.planName
        holder.planPrice.text = store.planPrice
        holder.reviewText.text = store.reviewText
        
        // クリックリスナーを設定
        holder.storeCard.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, StoreDetailActivity::class.java).apply {
                putExtra("store_id", store.id)
                putExtra("store_name", store.name)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = stores.size
} 