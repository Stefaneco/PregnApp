package com.example.pregnapp.auth.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pregnapp.exceptions.NoSessionException
import com.example.pregnapp.auth.interactors.AuthInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authInteractors: AuthInteractors
): ViewModel() {
    private val _uiState = MutableStateFlow<SplashScreenState>(SplashScreenState.Loading)
    val uiState: StateFlow<SplashScreenState> = _uiState

    init {
        viewModelScope.launch {
            try {
                authInteractors.getSessionFromDevice()
                _uiState.value = SplashScreenState.Success
            } catch (e: NoSessionException){
                _uiState.value = SplashScreenState.NoSession
            } catch (e: Exception){
                Log.e("SplashViewModel", e.message ?: "Unknown error")
                _uiState.value = SplashScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }
}