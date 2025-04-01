package com.example.myapplication.data.State

sealed class UserState {
    data object Loading: UserState()
    data object Init: UserState()
    data class Success(val message: String): UserState()
    data class Error(val message: String): UserState()
}