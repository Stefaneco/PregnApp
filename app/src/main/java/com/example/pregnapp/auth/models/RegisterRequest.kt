package com.example.pregnapp.auth.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
        )