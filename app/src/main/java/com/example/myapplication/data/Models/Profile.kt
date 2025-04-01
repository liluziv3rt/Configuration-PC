package com.example.myapplication.data.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val name:String,
    val surname:String,
    val id: String?
)