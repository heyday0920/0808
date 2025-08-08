package com.example.myapplication

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecentPractitionersRepository(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {
    private val maxRecentPractitioners = 5
    private val PREF_KEY = "practitioners_list"

    fun getRecentPractitioners(): List<RecentPractitioner> {
        val json = sharedPreferences.getString(PREF_KEY, "[]") ?: "[]"
        val type = object : TypeToken<List<RecentPractitioner>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun saveRecentPractitioner(practitioner: RecentPractitioner) {
        val currentList = getRecentPractitioners().toMutableList()

        // Remove existing practitioner with the same ID to avoid duplicates
        currentList.removeAll { it.id == practitioner.id }

        // Add the new practitioner to the beginning of the list
        currentList.add(0, practitioner)

        // Limit the list to the maximum size
        val limitedList = currentList.take(maxRecentPractitioners)

        // Save the updated list to SharedPreferences
        val json = gson.toJson(limitedList)
        sharedPreferences.edit().putString(PREF_KEY, json).apply()
    }
}
