package com.example.myapplication.data.Cartage

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.data.Components.ProductCard
import com.example.myapplication.data.Models.Elements

@Composable
fun CartageScreen(controlNav: NavController, id:Int, viewModelCartage: ViewModelCartage = viewModel{ ViewModelCartage(id) }){
    val element: Elements = viewModelCartage.allElement

    ProductCard(element)
}