package com.example.myapplication.data.Components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.Models.Types

@Composable
fun TypeButtons(typeElem: Types?, isSelected: Boolean, OnClick: () -> Unit) {
    var isSelectesNow by remember{ mutableStateOf(isSelected) }
    Button(
        onClick = {
            OnClick()
            isSelectesNow = !isSelectesNow
        },
        modifier = Modifier
            .padding(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelectesNow) Color.Blue else Color.Gray,
            contentColor = Color.White
        )
    ) {
        Text(typeElem?.name ?: "", fontSize = 18.sp)
    }
}