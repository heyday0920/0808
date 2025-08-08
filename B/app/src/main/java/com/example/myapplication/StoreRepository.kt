package com.example.myapplication

import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.FilterOperator

class StoreRepository(private val postgrest: Postgrest) {

    suspend fun getStores(): List<Store> {
        return try {
            postgrest.from("stores").select().decodeList()
        } catch (e: Exception) {
            android.util.Log.e("StoreRepository", "Error getting stores", e)
            emptyList()
        }
    }

    suspend fun getStoresByCategory(category: String): List<Store> {
        return try {
            postgrest.from("stores")
                .select {
                    filter("category", FilterOperator.EQ, category)
                }
                .decodeList()
        } catch (e: Exception) {
            android.util.Log.e("StoreRepository", "Error getting stores by category", e)
            emptyList()
        }
    }

    suspend fun getStoreById(storeId: String): Store? {
        return try {
            postgrest.from("stores")
                .select {
                    filter("id", FilterOperator.EQ, storeId)
                }
                .decodeSingleOrNull()
        } catch (e: Exception) {
            android.util.Log.e("StoreRepository", "Error getting store by ID", e)
            null
        }
    }

    suspend fun addStore(store: Store) {
        try {
            postgrest.from("stores").insert(store)
        } catch (e: Exception) {
            android.util.Log.e("StoreRepository", "Error adding store", e)
        }
    }
}