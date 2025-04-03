package com.example.mangaproj.data.network

import android.content.Context
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://mwitnjckdpqveuvnmitm.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im13aXRuamNrZHBxdmV1dm5taXRtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE2OTI0MTEsImV4cCI6MjA1NzI2ODQxMX0.DWelA_avKoFshzFXa5SJtVgOV0MvPVCN8FgnMKXFOfg"
    ) {
        install(Postgrest)
        install(Auth)
        install(Storage)
    }
}


