package com.example.myapplication.Main

import android.app.DownloadManager.Query
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.Const.Constant.supabase
import com.example.myapplication.data.Models.Elements
import com.example.myapplication.data.Models.Types
import com.example.myapplication.data.State.FilterState
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

class MainViewModel(): ViewModel() {

    private val _screenState = MutableStateFlow<ResultState>(ResultState.Loading)
    val screenState: StateFlow<ResultState> = _screenState.asStateFlow()

    private val _elements = MutableLiveData<List<Elements>>()
    val elements: LiveData<List<Elements>> get() = _elements

    private val _types = MutableLiveData<List<Types>>()
    val types: LiveData<List<Types>> get() = _types

    private var allElements: List<Elements> = listOf()

    private var _filtElem = MutableLiveData<List<Elements>>()
    val filtElem: LiveData<List<Elements>> get() = _filtElem

    private var _filtType: MutableList<Int> = mutableListOf()
    val filtType: List<Int> get() = _filtType

    init{
        refresh()
    }

    fun refresh(){
        loadELements()
        loadTypes()
    }

    private var filterState = mutableStateOf(FilterState())

    fun RememberFiltState(textSearch:String){
        filterState.value = filterState.value.copy(textSearch = textSearch, types = _filtType)
    }

    // Функция для загрузки элементов из базы данных
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

    // Функция для загрузки категорий из базы данных
    private fun loadTypes(){
        viewModelScope.launch {
            try {
                _types.value = supabase.postgrest.from("types").select().decodeList<Types>()
            }catch (_ex: AuthRestException){}
        }
    }

    // Функция для получения URL изображения товара
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

    fun filtElem(filtString:String){
        if(_filtType.isNotEmpty()){
            var filtered = allElements.filter { x -> x.name.contains(filtString) || x.desc.contains(filtString) }
            filtered = filtered.filter { x -> _filtType.contains(x.typeId)}
            _elements.value = filtered ?: emptyList()
        }
        else{
            val filtered = allElements.filter { x -> x.name.contains(filtString) || x.desc.contains(filtString) }
            _elements.value = filtered ?: emptyList()
        }
    }

    fun toggleType(typeId: Int, textSearch: String) {
        if (_filtType.contains(typeId)) {
            _filtType.remove(typeId)
        } else {
            _filtType.add(typeId)
        }
        filtElem(textSearch)
    }

}