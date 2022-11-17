package com.example.pregnapp.auth

import com.example.pregnapp.auth.models.LoginRequest
import com.example.pregnapp.auth.models.RegisterRequest
import io.ktor.client.statement.*

interface IAccountService {

    suspend fun login(loginRequest: LoginRequest) : HttpResponse
    suspend fun register(registerRequest: RegisterRequest) : HttpResponse
}