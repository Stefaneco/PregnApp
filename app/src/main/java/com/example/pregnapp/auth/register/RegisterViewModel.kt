package com.example.pregnapp.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pregnapp.auth.AuthScreenState
import com.example.pregnapp.auth.interactors.AuthInteractors
import com.example.pregnapp.auth.models.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authInteractors: AuthInteractors
) : ViewModel()  {

    private val _uiState = MutableStateFlow<AuthScreenState>(AuthScreenState.Static)
    val uiState: StateFlow<AuthScreenState> = _uiState

    fun register(name: String, email: String, password: String){
        if (isValidRegistration(name, email, password)){
            authInteractors.register(RegisterRequest(name, email, password)).onEach { dataState ->
                if(dataState.isLoading) _uiState.value = AuthScreenState.Loading
                else if (!dataState.message.isNullOrEmpty()){
                    _uiState.value = AuthScreenState.Error(dataState.message!!)
                    Log.e("LoginViewModel", dataState.message!!)
                }
                else _uiState.value = AuthScreenState.Success
            }.launchIn(viewModelScope)
        }
    }

    fun isValidRegistration(name: String, email: String, password: String) : Boolean{
        return isValidName(name) && isValidEmail(email) && isValidPassword(password)
    }

    fun isValidName(name: String) = authInteractors.isValidName(name)

    fun isValidEmail(email: String) = authInteractors.isValidEmail(email)

    fun isValidPassword(password: String) = authInteractors.isValidPassword(password)

    fun errorDisplayed(){
        _uiState.value = AuthScreenState.Static
    }
}