package com.example.myapplication

data class Practitioner(
    val id: String = "",
    val name: String = "",
    val tag: String = "", // 保有資格1
    val tag2: String = "", // 保有資格2
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val nearest: String = "",
    val plan: String = "",
    val review: String = "",
    val imageUrl: String = "", // プロフィール画像URL
    val course_image1: String = "", // コース画像1
    val course_image2: String = "", // コース画像2
    val course_image3: String = "", // コース画像3
    val course_image4: String = "", // コース画像4
    val course_image5: String = "", // コース画像5
    val speciality: String = "",
    val experience: String = "",
    val location: String = "",
    val price: String = "",
    val description: String = "",
    val category: String = "",
    val storeName: String = ""
)