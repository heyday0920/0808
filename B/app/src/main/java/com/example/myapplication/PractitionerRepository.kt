package com.example.myapplication

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects

class PractitionerRepository {
    private val db = FirebaseFirestore.getInstance()
    private val practitionersCollection = db.collection("practitioners")

    fun getPractitioners(onSuccess: (List<Practitioner>) -> Unit, onError: (Exception) -> Unit) {
        practitionersCollection
            .get()
            .addOnSuccessListener { documents ->
                try {
                    val practitioners = documents.toObjects<Practitioner>()
                    onSuccess(practitioners)
                } catch (e: Exception) {
                    android.util.Log.e("PractitionerRepository", "Error parsing documents: ${e.message}")
                    onError(e)
                }
            }
            .addOnFailureListener { exception ->
                android.util.Log.e("PractitionerRepository", "Firebase query failed: ${exception.message}")
                onError(exception)
            }
    }

    fun getPractitionersByCategory(category: String, onSuccess: (List<Practitioner>) -> Unit, onError: (Exception) -> Unit) {
        practitionersCollection
            .whereEqualTo("category", category)
            .get()
            .addOnSuccessListener { documents ->
                try {
                    val practitioners = documents.toObjects<Practitioner>()
                    android.util.Log.d("PractitionerRepository", "Found ${practitioners.size} practitioners for category: $category")
                    onSuccess(practitioners)
                } catch (e: Exception) {
                    android.util.Log.e("PractitionerRepository", "Error parsing documents: ${e.message}")
                    onError(e)
                }
            }
            .addOnFailureListener { exception ->
                android.util.Log.e("PractitionerRepository", "Firebase query failed: ${exception.message}")
                onError(exception)
            }
    }

    fun getPractitionerById(practitionerId: String, onSuccess: (Practitioner?) -> Unit, onError: (Exception) -> Unit) {
        practitionersCollection
            .document(practitionerId)
            .get()
            .addOnSuccessListener { document ->
                try {
                    val practitioner = document.toObject(Practitioner::class.java)
                    onSuccess(practitioner)
                } catch (e: Exception) {
                    onError(e)
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    fun addPractitioner(practitioner: Practitioner, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        practitionersCollection
            .add(practitioner)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
} 