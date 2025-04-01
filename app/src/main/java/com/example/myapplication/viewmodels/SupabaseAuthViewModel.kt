package com.example.mangaproj.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.State.UserState
import com.example.mangaproj.data.network.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SupabaseAuthViewModel: ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Init)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    fun signUp(
        userEmail: String,
        userPassword: String,
    ){
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signUpWith(Email){
                    email = userEmail
                    password = userPassword
                }
                _userState.value = UserState.Success("Succsessful register new user!")
            } catch (e: Exception){
                _userState.value = UserState.Error("Error: тут ваще капец")
            }
        }
    }

    fun signIn(
        userEmail: String,
        userPassword: String,
    ){
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signInWith(Email){
                    email = userEmail
                    password = userPassword
                }
                _userState.value = UserState.Success("Succsessful log in!")

            } catch (e: Exception){
                _userState.value = UserState.Error("Error: ооочень длинная ошибка")
            }
        }
    }


}