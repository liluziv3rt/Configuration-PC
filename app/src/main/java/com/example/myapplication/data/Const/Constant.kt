package com.example.myapplication.data.Const

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage


object Constant {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://mwitnjckdpqveuvnmitm.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im13aXRuamNrZHBxdmV1dm5taXRtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE2OTI0MTEsImV4cCI6MjA1NzI2ODQxMX0.DWelA_avKoFshzFXa5SJtVgOV0MvPVCN8FgnMKXFOfg"
    ) {
        install(Postgrest)
        install(Auth)
        install(Storage)
    }
}

