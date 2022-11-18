package com.example.pregnapp.auth.interactors

class IsValidPassword() {
    operator fun invoke(password: String): Boolean{
        return password.length in 8..50
    }
}