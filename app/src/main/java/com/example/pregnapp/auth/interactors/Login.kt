package com.example.pregnapp.auth.interactors

import com.example.pregnapp.auth.IAccountService
import com.example.pregnapp.auth.models.LoginRequest
import com.example.pregnapp.network.ISessionSource
import com.example.pregnapp.network.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Login(
    private val accountService: IAccountService,
    private val sessionSource: ISessionSource
) {
    operator fun invoke(loginRequest: LoginRequest) : Flow<RequestState<Unit>> = flow {

        emit(RequestState.loading())
        try{
            val sessionData = accountService.login(loginRequest)
            sessionSource.updateSessionData(sessionData)
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}