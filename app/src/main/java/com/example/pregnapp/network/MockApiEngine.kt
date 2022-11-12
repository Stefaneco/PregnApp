package com.example.pregnapp.network

import android.util.Log
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*

object MockApiEngine {

    private val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
    private var errorStatusCode = HttpStatusCode.BadRequest

    fun get() = engine

    private var isSuccess = true

    fun givenFailure(statusCode: HttpStatusCode = HttpStatusCode.BadRequest){
        isSuccess = false
        errorStatusCode = statusCode
    }

    fun givenSuccess(){
        isSuccess = true
    }

    private val engine = MockEngine { request ->
        handleSearchRequest(request)
    }

    private fun MockRequestHandleScope.handleSearchRequest(request: HttpRequestData): HttpResponseData {
        if (!isSuccess) return errorResponse()
        return when(request.url.encodedPath){
            "/api/account/login" -> respond(mockTokenResponse, HttpStatusCode.OK, responseHeaders)
            "/api/account/refresh" -> respond(mockTokenResponse, HttpStatusCode.OK, responseHeaders)
            else -> {
                Log.e("MockApiEngine.kt", request.url.encodedPath)
                error("Unhandled ${request.url.encodedPath}")
            }
        }
    }

    private fun MockRequestHandleScope.errorResponse(): HttpResponseData {
        return respond(
            content = "",
            status = errorStatusCode,
            headers = responseHeaders
        )
    }

    private val mockTokenResponse = """{
        |"jwt":"jwt",
        |"refreshToken":"refreshToken"
        |}""".trimMargin()

}