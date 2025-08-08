package com.example.myapplication

import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email

class AuthRepository(private val gotrue: GoTrue) {

    suspend fun signUp(email: String, password: String): Boolean {
        return try {
            gotrue.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            android.util.Log.e("AuthRepository", "Sign up failed", e)
            false
        }
    }

    suspend fun signIn(email: String, password: String): Boolean {
        return try {
            gotrue.signInWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
            android.util.Log.e("AuthRepository", "Sign in failed", e)
            false
        }
    }

    fun signOut() {
        // This is not a suspend function
        // gotrue.logout()
        // As of now, the library seems to have a suspend version of logout
        // Will implement it in the viewmodel scope
    }

    fun getCurrentUser() = gotrue.currentUserOrNull()
}
