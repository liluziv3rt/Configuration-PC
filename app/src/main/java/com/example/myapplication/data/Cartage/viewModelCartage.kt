package com.example.myapplication.data.Cartage


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Const.Constant.supabase
import com.example.myapplication.data.Models.Elements
import com.example.myapplication.data.State.ResultState
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModelCartage(element:Int): ViewModel() {

    private val _screenState = MutableStateFlow<ResultState>(ResultState.Loading)
    val screenState: StateFlow<ResultState> = _screenState.asStateFlow()

    val elementf:Int = element

    var allElement: Elements = Elements()

    init{
        refresh()
    }

    fun refresh(){
        loadELements()
    }

    // Функция для загрузки элементов из базы данных
    private fun loadELements(){
        _screenState.value = ResultState.Loading
        viewModelScope.launch {
            try {
                allElement = supabase.postgrest.from("elements").select{ filter { eq("id", value = elementf) }}.decodeSingle<Elements>()
            }catch (_ex: AuthRestException){
                _screenState.value = ResultState.Error(_ex.errorDescription)
            }
        }
    }

}