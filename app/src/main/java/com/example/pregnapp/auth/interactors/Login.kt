package com.example.pregnapp.auth.interactors

import com.example.pregnapp.auth.IAccountService
import com.example.pregnapp.auth.models.ISessionSource
import com.example.pregnapp.auth.models.LoginRequest
import com.example.pregnapp.exceptions.BadRequestException
import com.example.pregnapp.exceptions.InternalServerErrorException
import com.example.pregnapp.network.RequestState
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Login(
    private val accountService: IAccountService,
    private val sessionSource: ISessionSource
) {
    operator fun invoke(loginRequest: LoginRequest) : Flow<RequestState<Unit>> = flow {

        emit(RequestState.loading())
        try{
            val loginResponse = accountService.login(loginRequest)
            if(loginResponse.status == HttpStatusCode.BadRequest) throw BadRequestException()
            if(loginResponse.status == HttpStatusCode.InternalServerError) throw InternalServerErrorException()
            sessionSource.updateSessionData(loginResponse.body())
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}