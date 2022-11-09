package com.example.pregnapp.auth.splash

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pregnapp.ui.components.LoadingDotsAnimation

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        LoadingDotsAnimation(circleSize = 15.dp)
    }

    when(val state = viewModel.uiState.collectAsState().value){
        is SplashScreenState.Success -> {
            Log.e("SplashScreen.kt", "SplashScreenState.Success")
            navController.navigate("profile") {
                popUpTo("splash"){
                    inclusive = true
                }
            }
        }
        is SplashScreenState.NoSession -> {
            navController.navigate("login") {
                popUpTo("splash"){
                    inclusive = true
                }
            }
        }
        is SplashScreenState.Error -> {
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            (context as? Activity)?.finish()
        }
        else -> {}
    }
}