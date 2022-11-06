package com.example.pregnapp.auth

import com.example.pregnapp.auth.models.LoginRequest
import com.example.pregnapp.network.SessionData

interface IAccountService {

    suspend fun login(loginRequest: LoginRequest) : SessionData
}