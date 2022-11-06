package com.example.pregnapp.auth

import com.example.pregnapp.auth.models.LoginRequest
import com.example.pregnapp.network.IHttpRoutes
import com.example.pregnapp.network.SessionData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AccountService (
    private val client: HttpClient,
    private val httpRoutes: IHttpRoutes
): IAccountService {
    override suspend fun login(loginRequest: LoginRequest): SessionData {
        return client.post(httpRoutes.login()) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }
}