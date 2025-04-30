package com.example.myapplication.data.Models

import kotlinx.serialization.Serializable

@Serializable
data class Elements(
    val id:Int? = null,
    val name:String = "",
    val desc:String = "",
    val price:Int = 1,
    val image: String? = "",
    val manufacturer: String = "",
    val typeId: Int = 1,
    val briefDesc: String = ""
)