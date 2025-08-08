package com.example.myapplication

data class Store(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val images: List<String> = emptyList(),
    val planName: String = "",
    val planPrice: String = "",
    val ownerName: String = "",
    val location: String = "",
    val reviewText: String = "",
    val reviewCategory: String = "",
    val category: String = "",
    val access: String = "",
    val address: String = "",
    val station: String = ""
)