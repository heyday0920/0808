package com.example.myapplication

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class StoreRepository(private val db: FirebaseFirestore) {

    private val storesCollection = db.collection("stores")

    suspend fun getStores(): List<Store> {
        return try {
            val documents = storesCollection.get().await()
            documents.toObjects()
        } catch (e: Exception) {
            // In a real app, you might want to re-throw a custom exception
            // or handle it more gracefully.
            android.util.Log.e("StoreRepository", "Error getting stores", e)
            emptyList()
        }
    }

    suspend fun getStoresByCategory(category: String): List<Store> {
        return try {
            val documents = storesCollection
                .whereEqualTo("category", category)
                .get()
                .await()
            android.util.Log.d("StoreRepository", "Found ${documents.size()} stores for category: $category")
            documents.toObjects()
        } catch (e: Exception) {
            android.util.Log.e("StoreRepository", "Error getting stores by category", e)
            emptyList()
        }
    }

    suspend fun getStoreById(storeId: String): Store? {
        return try {
            val document = storesCollection
                .document(storeId)
                .get()
                .await()
            document.toObject(Store::class.java)
        } catch (e: Exception) {
            android.util.Log.e("StoreRepository", "Error getting store by ID", e)
            null
        }
    }

    suspend fun addStore(store: Store) {
        try {
            storesCollection.add(store).await()
        } catch (e: Exception) {
            android.util.Log.e("StoreRepository", "Error adding store", e)
            // Handle error, maybe rethrow as a custom exception
        }
    }
}