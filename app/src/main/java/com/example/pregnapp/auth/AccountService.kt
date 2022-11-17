package com.example.pregnapp.auth

import com.example.pregnapp.auth.models.LoginRequest
import com.example.pregnapp.auth.models.RegisterRequest
import com.example.pregnapp.network.IHttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AccountService (
    private val client: HttpClient,
    private val httpRoutes: IHttpRoutes
): IAccountService {
    override suspend fun login(loginRequest: LoginRequest): HttpResponse {
        return client.post(httpRoutes.login()) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }

    override suspend fun register(registerRequest: RegisterRequest): HttpResponse {
        return client.post(httpRoutes.register()){
            contentType(ContentType.Application.Json)
            setBody(registerRequest)
        }.body()
    }
}