package com.example.pregnapp.auth.interactors

class IsValidName {
    operator fun invoke(password: String): Boolean{
        return password.length in 2..50
    }
}