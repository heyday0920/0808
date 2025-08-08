package com.example.myapplication

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class PractitionerRepository(private val db: FirebaseFirestore) {

    private val practitionersCollection = db.collection("practitioners")

    suspend fun getPractitioners(): List<Practitioner> {
        return try {
            val documents = practitionersCollection.get().await()
            documents.toObjects()
        } catch (e: Exception) {
            android.util.Log.e("PractitionerRepository", "Error getting practitioners", e)
            emptyList()
        }
    }

    suspend fun getPractitionersByCategory(category: String): List<Practitioner> {
        return try {
            val documents = practitionersCollection
                .whereEqualTo("category", category)
                .get()
                .await()
            android.util.Log.d("PractitionerRepository", "Found ${documents.size()} practitioners for category: $category")
            documents.toObjects()
        } catch (e: Exception) {
            android.util.Log.e("PractitionerRepository", "Error getting practitioners by category", e)
            emptyList()
        }
    }

    suspend fun getPractitionerById(practitionerId: String): Practitioner? {
        return try {
            val document = practitionersCollection
                .document(practitionerId)
                .get()
                .await()
            document.toObject(Practitioner::class.java)
        } catch (e: Exception) {
            android.util.Log.e("PractitionerRepository", "Error getting practitioner by ID", e)
            null
        }
    }

    suspend fun addPractitioner(practitioner: Practitioner) {
        try {
            practitionersCollection.add(practitioner).await()
        } catch (e: Exception) {
            android.util.Log.e("PractitionerRepository", "Error adding practitioner", e)
            // Handle error
        }
    }
} 