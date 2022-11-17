package com.example.pregnapp.auth.interactors

data class AuthInteractors(
    val getSessionFromDevice: GetSessionFromDevice,
    val login: Login,
    val isValidEmail: IsValidEmail,
    val isValidPassword: IsValidPassword,
    val isValidName: IsValidName,
    val register: Register
)
