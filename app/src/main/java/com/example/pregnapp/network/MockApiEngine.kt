package com.example.pregnapp.network

import android.util.Log
import io.ktor.client.engine.mock.*
import io.ktor.http.*

object MockApiEngine {

    private val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))

    fun create() = engine

    private val engine = MockEngine {

        request ->
        when(request.url.encodedPath){
            "/api/account/login" -> respond(mockTokenResponse, HttpStatusCode.OK, responseHeaders)
            "/api/account/refresh" -> respond(mockTokenResponse, HttpStatusCode.OK, responseHeaders)
            else -> {
                Log.e("MockApiEngine.kt", request.url.encodedPath)
                error("Unhandled ${request.url.encodedPath}")
            }
        }
    }

    private val mockTokenResponse = """{
        |"jwt":"jwt",
        |"refreshToken":"refreshToken"
        |}""".trimMargin()

}