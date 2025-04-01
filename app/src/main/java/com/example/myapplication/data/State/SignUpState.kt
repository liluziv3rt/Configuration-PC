package com.example.myapplication.data.State

data class SignUpState(
    val email: String = "",
    val name: String = "",
    val surname: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    var isEmailError:Boolean = false
)