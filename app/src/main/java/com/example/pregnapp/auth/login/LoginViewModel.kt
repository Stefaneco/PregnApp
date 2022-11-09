package com.example.pregnapp.auth.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pregnapp.auth.AuthScreenState
import com.example.pregnapp.auth.interactors.AuthInteractors
import com.example.pregnapp.auth.models.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authInteractors: AuthInteractors
) : ViewModel()
{
    private val _uiState = MutableStateFlow<AuthScreenState>(AuthScreenState.Static)
    val uiState: StateFlow<AuthScreenState> = _uiState
    var isNavigatedOut = false

    fun logIn(email: String, password: String){
        if(!isValidEmail(email) || !isValidPassword(password)) return
        authInteractors.login(LoginRequest(email, password)).onEach { dataState ->
            if(dataState.isLoading) _uiState.value = AuthScreenState.Loading
            else if (!dataState.message.isNullOrEmpty()){
                _uiState.value = AuthScreenState.Error(dataState.message!!)
                Log.e("LoginViewModel", dataState.message!!)
            }
            else _uiState.value = AuthScreenState.Success
        }.launchIn(viewModelScope)
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun errorDisplayed(){
        _uiState.value = AuthScreenState.Static
    }
}