package com.example.pregnapp.auth.interactors

import com.example.pregnapp.auth.IAccountService
import com.example.pregnapp.auth.models.ISessionSource
import com.example.pregnapp.auth.models.RegisterRequest
import com.example.pregnapp.exceptions.BadRequestException
import com.example.pregnapp.exceptions.InternalServerErrorException
import com.example.pregnapp.network.RequestState
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Register(
    private val accountService: IAccountService,
    private val sessionSource: ISessionSource
) {
    operator fun invoke(registerRequest: RegisterRequest) : Flow<RequestState<Unit>> = flow {
        emit(RequestState.loading())
        try{
            val registerResponse = accountService.register(registerRequest)
            if(registerResponse.status == HttpStatusCode.BadRequest) throw BadRequestException()
            if(registerResponse.status == HttpStatusCode.InternalServerError) throw InternalServerErrorException()
            sessionSource.updateSessionData(registerResponse.body())
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}