package com.example.myapplication.Main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.data.Components.TypeButtons
import com.example.myapplication.data.Components.elementCard
import fieldSearch
import io.github.jan.supabase.auth.auth
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MainScreen(controlNav: NavController, viewModelMainScreen: MainViewModel = viewModel{ MainViewModel() }
) {
    val textSearch = remember { mutableStateOf("") }
    val actualState by viewModelMainScreen.screenState.collectAsState()
    val element = viewModelMainScreen.elements.observeAsState(emptyList())
    val types = viewModelMainScreen.types.observeAsState(emptyList())

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .zIndex(1f)
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(top = 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    fieldSearch(
                        myText = "Найти",
                        text = textSearch.value,
                        onValueChange = {
                            textSearch.value = it
                            viewModelMainScreen.filtElem(it)
                        }
                    )
                }

                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(types.value.size) { index ->
                        TypeButtons(
                            typeElem = types.value[index].copy(),
                            OnClick = {
                                viewModelMainScreen.toggleType(
                                    typeId = types.value[index].id,
                                    textSearch = textSearch.value
                                )
                            },
                            isSelected = viewModelMainScreen.filtType.contains(types.value[index].id)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }


                LazyColumn {
                    items(element.value.size) { index ->
                        elementCard(element.value[index])
                    }
                }
            }
        }
    }
}
