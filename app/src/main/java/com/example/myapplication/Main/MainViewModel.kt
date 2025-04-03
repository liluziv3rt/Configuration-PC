package com.example.myapplication.Main

import android.app.DownloadManager.Query
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.Const.Constant.supabase
import com.example.myapplication.data.Models.Elements
import com.example.myapplication.data.Models.Types
import com.example.myapplication.data.State.ResultState
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import io.ktor.events.Events
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {

    private val _screenState = MutableStateFlow<ResultState>(ResultState.Loading)
    val screenState: StateFlow<ResultState> = _screenState.asStateFlow()

    private val _elements = MutableLiveData<List<Elements>>()
    val elements: LiveData<List<Elements>> get() = _elements

    private val _types = MutableLiveData<List<Types>>()
    val types: LiveData<List<Types>> get() = _types

    private var allElements: List<Elements> = listOf()

    init{
        loadELements()
        loadTypes()
    }

    private fun loadELements(){
        _screenState.value = ResultState.Loading
        viewModelScope.launch {
            try {
                allElements = supabase.postgrest.from("elements").select().decodeList<Elements>()
                _elements.value = allElements
            }catch (_ex: AuthRestException){
                _screenState.value = ResultState.Error(_ex.errorDescription)
            }
        }
    }

    private fun loadTypes(){
        viewModelScope.launch {
            try {
                _types.value = supabase.postgrest.from("types").select().decodeList<Types>()
            }catch (_ex: AuthRestException){}
        }
    }

    suspend fun getUrlImage(elementName: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val url = supabase.storage.from("Books").publicUrl("${elementName}.png")
                Log.d("buck", url)
                url
            } catch (ex: AuthRestException) {
                Log.e("Error", "Failed to get URL: ${ex.message}")
                ""
            }
        }
    }

    fun filterList(query: String?, idType: Int?){
        val filteredElements = allElements.filter { element ->
            val matchesTitle = query.isNullOrEmpty() || element.name.contains(query, ignoreCase = true) || element.desc.contains(query, ignoreCase = true)
            val matchesTypes = idType == -1 || element.typeId == idType
            matchesTitle && matchesTypes
        }
        _elements.value = filteredElements
    }
}