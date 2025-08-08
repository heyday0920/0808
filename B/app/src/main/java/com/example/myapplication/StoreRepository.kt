package com.example.myapplication

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects

class StoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storesCollection = db.collection("stores")

    fun getStores(onSuccess: (List<Store>) -> Unit, onError: (Exception) -> Unit) {
        storesCollection
            .get()
            .addOnSuccessListener { documents ->
                try {
                    val stores = documents.toObjects<Store>()
                    onSuccess(stores)
                } catch (e: Exception) {
                    onError(e)
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun getStoresByCategory(category: String, onSuccess: (List<Store>) -> Unit, onError: (Exception) -> Unit) {
        storesCollection
            .whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { documents ->
                try {
                    val stores = documents.toObjects<Store>()
                    android.util.Log.d("StoreRepository", "Found ${stores.size} stores for category: $category")
                    onSuccess(stores)
                } catch (e: Exception) {
                    android.util.Log.e("StoreRepository", "Error parsing documents: ${e.message}")
                    onError(e)
                }
            }
            .addOnFailureListener { exception ->
                android.util.Log.e("StoreRepository", "Firebase query failed: ${exception.message}")
                onError(exception)
            }
    }

    fun getStoreById(storeId: String, onSuccess: (Store?) -> Unit, onError: (Exception) -> Unit) {
        storesCollection
            .document(storeId)
            .get()
            .addOnSuccessListener { document ->
                try {
                    val store = document.toObject(Store::class.java)
                    onSuccess(store)
                } catch (e: Exception) {
                    onError(e)
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun addStore(store: Store, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        storesCollection
            .add(store)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
} 