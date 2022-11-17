package com.example.pregnapp.auth.splash

import android.app.Activity
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pregnapp.ui.components.LoadingDotsAnimation
import com.example.pregnapp.util.NavigationRoutes

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
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
            navController.navigate(NavigationRoutes.PROFILE) {
                popUpTo(NavigationRoutes.SPLASH){
                    inclusive = true
                }
            }
        }
        is SplashScreenState.NoSession -> {
            navController.navigate(NavigationRoutes.LOGIN) {
                popUpTo(NavigationRoutes.SPLASH){
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