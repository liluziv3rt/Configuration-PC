package com.example.myapplication.data.Models

import kotlinx.serialization.Serializable

@Serializable
data class Elements(
    val id:Int?,
    val name:String,
    val desc:String,
    val price:Int,
    val image: String?,
    val manufacturer: String,
    val typeId: Int,
    val briefDesc: String
)