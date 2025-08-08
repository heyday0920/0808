package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PractitionerAdapter(private val list: List<Practitioner>) : RecyclerView.Adapter<PractitionerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.practitionerImage)
        val name: TextView = view.findViewById(R.id.practitionerName)
        val rating: TextView = view.findViewById(R.id.practitionerRating)
        val reviewCount: TextView = view.findViewById(R.id.practitionerReviewCount)
        val plan: TextView = view.findViewById(R.id.practitionerPlan)
        val price: TextView = view.findViewById(R.id.practitionerPrice)
        val review: TextView = view.findViewById(R.id.practitionerReview)
        val tag1: TextView = view.findViewById(R.id.practitionerTag1)
        val tag2: TextView = view.findViewById(R.id.practitionerTag2)
        val courseImage1: ImageView = view.findViewById(R.id.practitionerCourseImage1)
        val courseImage2: ImageView = view.findViewById(R.id.practitionerCourseImage2)
        val courseImage3: ImageView = view.findViewById(R.id.practitionerCourseImage3)
        val courseImage4: ImageView = view.findViewById(R.id.practitionerCourseImage4)
        val courseImage5: ImageView = view.findViewById(R.id.practitionerCourseImage5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_practitioner, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.rating.text = item.rating.toString()
        holder.reviewCount.text = item.reviewCount.toString()
        holder.plan.text = item.plan
        holder.price.text = item.price
        holder.review.text = item.review

        // Set tag (保有資格) from database
        holder.tag1.text = item.tag
        
        // Show tag2 only if it's not empty
        if (item.tag2.isNotEmpty()) {
            holder.tag2.text = item.tag2
            holder.tag2.visibility = android.view.View.VISIBLE
        } else {
            holder.tag2.visibility = android.view.View.GONE
        }

        // Load profile image from database
        if (item.imageUrl.isNotEmpty()) {
            android.util.Log.d("PractitionerAdapter", "Loading profile image: ${item.imageUrl}")
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .placeholder(R.color.background_card)
                .error(R.color.background_card)
                .into(holder.image)
        } else {
            android.util.Log.d("PractitionerAdapter", "Profile image URL is empty")
            holder.image.setImageResource(R.color.background_card)
        }

        // Load course images from database
        val courseImages = listOf(
            item.course_image1,
            item.course_image2,
            item.course_image3,
            item.course_image4,
            item.course_image5
        )
        
        val courseImageViews = listOf(
            holder.courseImage1,
            holder.courseImage2,
            holder.courseImage3,
            holder.courseImage4,
            holder.courseImage5
        )
        
        // Load each course image if URL is not empty, hide if empty
        courseImages.forEachIndexed { index, imageUrl ->
            if (imageUrl.isNotEmpty()) {
                courseImageViews[index].visibility = android.view.View.VISIBLE
                android.util.Log.d("PractitionerAdapter", "Loading course image ${index + 1}: $imageUrl")
                
                // Try to load from URL first, fallback to local resource if failed
                Glide.with(holder.itemView.context)
                    .load(imageUrl)
                    .placeholder(R.color.background_card)
                    .error(R.color.background_card)
                    .into(courseImageViews[index])
                    
                // Alternative: Use local drawable resources for testing
                // courseImageViews[index].setImageResource(R.drawable.ic_spa)
            } else {
                courseImageViews[index].visibility = android.view.View.GONE
                android.util.Log.d("PractitionerAdapter", "Hiding course image ${index + 1}: empty URL")
            }
        }

        // Log images for debugging
        android.util.Log.d("PractitionerAdapter", "Practitioner: ${item.name}, ProfileImage: ${item.imageUrl}, CourseImages: $courseImages")

        // クリックで詳細画面へ遷移
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PractitionerDetailActivity::class.java)
            // デバッグ用：クリックされた施術者の情報をログに出力
            android.util.Log.d("PractitionerAdapter", "クリックされた施術者: ${item.name}")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = list.size
} 