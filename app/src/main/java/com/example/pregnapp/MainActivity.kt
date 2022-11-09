package com.example.pregnapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pregnapp.auth.login.LoginScreen
import com.example.pregnapp.auth.splash.SplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pregnapp.auth.register.RegisterScreen
import com.example.pregnapp.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "splash"){
                composable("splash"){
                    SplashScreen(
                        viewModel = hiltViewModel(),
                        navController = navController
                    )
                }

                composable("login"){
                    LoginScreen(
                        viewModel = hiltViewModel(),
                        navController = navController
                    )
                }

                composable("register"){
                    RegisterScreen()
                }
                composable("profile"){
                    ProfileScreen()
                }
            }
        }
    }
}