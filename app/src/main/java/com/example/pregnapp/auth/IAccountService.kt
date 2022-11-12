package com.example.pregnapp.auth

import com.example.pregnapp.auth.models.LoginRequest
import io.ktor.client.statement.*

interface IAccountService {

    suspend fun login(loginRequest: LoginRequest) : HttpResponse
}