package com.example.myapplication

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

object Supabase {
    private const val SUPABASE_URL = "https://utxsqoysrrwbomnxidmbg.supabase.co"
    private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InV0eHNxb3lzcnJ3Ym9tbnhkbWJnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTQ2NDMxMDQsImV4cCI6MjA3MDIxOTEwNH0.iEgtodxACHSrxXI-WdBm30rCjod9dQyWXotngiyHplc"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(GoTrue)
            install(Postgrest)
        }
    }
}
