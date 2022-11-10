package com.example.pregnapp.auth.interactors

import android.util.Patterns

class IsValidEmail() {
    operator fun invoke(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}