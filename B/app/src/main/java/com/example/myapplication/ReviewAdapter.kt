package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skillRating: TextView = itemView.findViewById(R.id.skill_rating)
        val hospitalityRating: TextView = itemView.findViewById(R.id.hospitality_rating)
        val responseRating: TextView = itemView.findViewById(R.id.response_rating)
        val cleanlinessRating: TextView = itemView.findViewById(R.id.cleanliness_rating)
        val reviewLocation: TextView = itemView.findViewById(R.id.review_location)
        val reviewPlan: TextView = itemView.findViewById(R.id.review_plan)
        val reviewPrice: TextView = itemView.findViewById(R.id.review_price)
        val userImage: ImageView = itemView.findViewById(R.id.user_image)
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val reviewText: TextView = itemView.findViewById(R.id.review_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        
        // Set rating scores
        holder.skillRating.text = "${review.skillRating.toInt()}/5"
        holder.hospitalityRating.text = "${review.hospitilityRating.toInt()}/5"
        holder.responseRating.text = "${review.responseRating.toInt()}/5"
        holder.cleanlinessRating.text = "${review.cleanlinessRating.toInt()}/5"
        
        // Set plan and location info
        holder.reviewLocation.text = review.location
        holder.reviewPlan.text = review.planName
        holder.reviewPrice.text = review.price
        
        // Set user info
        holder.userName.text = review.userName
        
        // Set review text
        holder.reviewText.text = review.reviewText
    }

    override fun getItemCount(): Int = reviews.size
} 