package com.example.myapplication.Registration

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Const.Constant
import com.example.myapplication.data.Const.Constant.supabase
import com.example.myapplication.data.Models.Profile
import com.example.myapplication.data.State.ResultState
import com.example.myapplication.data.State.SignUpState
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel: ViewModel() {
    private val _uiState = mutableStateOf(SignUpState())   // состояние UI (ввода) приватное (не может изменяться из вне)
    val uiState: SignUpState get() = _uiState.value        // состояние UI публичное

    private val _screenState = MutableStateFlow<ResultState>(ResultState.Initialized) //тоже самое, но для данного скрина выполнения функции с регистрацией
    val screenState: StateFlow<ResultState> = _screenState.asStateFlow()

    fun updateState(newState: SignUpState) {            //Функция для обновления состояния
        _uiState.value = newState                       //Значение состояния граф. интерфейса принадлежит SignUpState
        _screenState.value = ResultState.Initialized // Значение скрина меняется на инициализированное
    }

    // Основная функция, которая обрабатывает процесс регистрации в системе пользователя
    fun signUp()
    {
        _screenState.value = ResultState.Loading  //Направленно на чтение значение _resultState теперь равно состоянию загрузки
        if (_uiState.value.email.isEmailValid()) {
            if (_uiState.value.password == _uiState.value.confirmPassword){
                viewModelScope.launch {                     //Запускается асинхронная операция, чтобы не блокировать основной поток.
                    try {                                   //Если введеныые пароли совпадают и isEmailError = true (нет ошибок при введении почты)
                        supabase.auth.signUpWith(Email)
                        {
                            email = _uiState.value.email            //Значение почты примет введеное значение
                            password = _uiState.value.password      //Значение пароля примет введеное значение
                        }
                        Log.d("SignUp", "Success")      //Выводит в лог сообщение с меткой "СингАп"

                        val userId = supabase.auth.currentUserOrNull()

                        if(userId != null) {
                            val user = Profile(
                                _uiState.value.name,    //Переменная "пользователь", которая потом будет передаваться в таблицу в SupaBase
                                _uiState.value.surname,
                                userId.id)
                            supabase.from("profiles").insert(user)
                            _screenState.value = ResultState.Success("Success")  //Изменение фактического значения состояния скрина. Оно меняется на "Успешно"
                        }
                        else{
                            _screenState.value = ResultState.Error( "Ошибка с айди юзера")
                        }
                    } catch (_ex: AuthRestException) {
                        Log.d("signUp", _ex.message.toString())     //Логирование ошибки, что она произошла в регистрации
                        Log.d("signUp", _ex.errorCode.toString())
                        Log.d("signUp", _ex.errorDescription.toString())
                        Log.d("REG_DEBUG", "surname: [${_uiState.value.name}] (length=${_uiState.value.name.length})")

                        _screenState.value = ResultState.Error(_ex.errorDescription ?: "Ошибка получения данных")   // Сообщение исключения и изменение состояния
                    }
                    catch(ex: Exception){
                        _screenState.value = ResultState.Error(ex.message ?: "Ошибка получения данных")
                    }
                }
            }
            else
            {
                _screenState.value = ResultState.Error( "Ошибка ввода пароля")
                Log.d("REG_DEBUG", "surname: [${_uiState.value.name}] (length=${_uiState.value.name.length})")

            }
        }
            else
        {
            _screenState.value = ResultState.Error( "Ошибка ввода почты")       //Если не проходит проверка почты или пароля, вылазит ошибка
        }
    }
}