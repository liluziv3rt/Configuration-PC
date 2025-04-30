package com.example.myapplication.data.State

data class FilterState (
    val textSearch: String = "",
    val types: List<Int> = listOf()
)