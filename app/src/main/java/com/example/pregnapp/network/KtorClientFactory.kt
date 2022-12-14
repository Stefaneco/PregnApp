package com.example.pregnapp.network

import com.example.pregnapp.auth.models.ISessionSource
import com.example.pregnapp.auth.models.SessionData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorClientFactory(
    private val sessionSource: ISessionSource,
    private val httpRoutes: IHttpRoutes,
    private val engine: HttpClientEngine
) {
    fun build(): HttpClient {
        val tokenClient = HttpClient(engine){
            install(ContentNegotiation){
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }

        val client = HttpClient(engine){
            install(ContentNegotiation){
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        try{
                            val session = sessionSource.getSessionData()
                            BearerTokens(session.jwt, session.refreshToken)
                        } catch (e: Exception){
                            null
                        }
                    }
                    refreshTokens {
                        val oldSession = sessionSource.getSessionData()
                        val newSession : SessionData = tokenClient.post(httpRoutes.refresh()){
                            contentType(ContentType.Application.Json)
                            setBody(oldSession)
                        }.body()
                        sessionSource.updateSessionData(newSession)
                        BearerTokens(newSession.jwt, newSession.refreshToken)
                    }

                }
            }
        }
        return client
    }

}