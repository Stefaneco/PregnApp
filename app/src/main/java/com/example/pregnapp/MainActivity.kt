package com.example.pregnapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pregnapp.auth.login.LoginScreen
import com.example.pregnapp.auth.splash.SplashScreen
import com.example.pregnapp.auth.register.RegisterScreen
import com.example.pregnapp.profile.ProfileScreen
import com.example.pregnapp.util.NavigationRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH){
                composable(NavigationRoutes.SPLASH){
                    SplashScreen(
                        navController = navController
                    )
                }

                composable(NavigationRoutes.LOGIN){
                    LoginScreen(
                        navController = navController
                    )
                }

                composable(NavigationRoutes.REGISTER){
                    RegisterScreen(
                        navController = navController
                    )
                }

                composable(NavigationRoutes.PROFILE){
                    ProfileScreen()
                }
            }
        }
    }
}