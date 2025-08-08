package com.example.myapplication

import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.FilterOperator

class PractitionerRepository(private val postgrest: Postgrest) {

    suspend fun getPractitioners(): List<Practitioner> {
        return try {
            postgrest.from("practitioners").select().decodeList()
        } catch (e: Exception) {
            android.util.Log.e("PractitionerRepository", "Error getting practitioners", e)
            emptyList()
        }
    }

    suspend fun getPractitionersByCategory(category: String): List<Practitioner> {
        return try {
            postgrest.from("practitioners")
                .select {
                    filter("category", FilterOperator.EQ, category)
                }
                .decodeList()
        } catch (e: Exception) {
            android.util.Log.e("PractitionerRepository", "Error getting practitioners by category", e)
            emptyList()
        }
    }

    suspend fun getPractitionerById(practitionerId: String): Practitioner? {
        return try {
            postgrest.from("practitioners")
                .select {
                    filter("id", FilterOperator.EQ, practitionerId)
                }
                .decodeSingleOrNull()
        } catch (e: Exception) {
            android.util.Log.e("PractitionerRepository", "Error getting practitioner by ID", e)
            null
        }
    }

    suspend fun addPractitioner(practitioner: Practitioner) {
        try {
            postgrest.from("practitioners").insert(practitioner)
        } catch (e: Exception) {
            android.util.Log.e("PractitionerRepository", "Error adding practitioner", e)
        }
    }
} 