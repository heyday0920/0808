package com.example.myapplication

data class Review(
    val id: Int,
    val userImage: String?,
    val userName: String,
    val date: String,
    val overallRating: Float,
    val skillRating: Float,
    val hospitilityRating: Float,
    val responseRating: Float,
    val cleanlinessRating: Float,
    val location: String,
    val planName: String,
    val price: String,
    val reviewText: String,
    val reviewImages: List<String>
) 