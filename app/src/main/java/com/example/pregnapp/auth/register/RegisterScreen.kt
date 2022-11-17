package com.example.pregnapp.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pregnapp.R
import com.example.pregnapp.auth.AuthScreenState
import com.example.pregnapp.ui.components.LoadingDotsAnimation
import com.example.pregnapp.ui.components.ValidatedPasswordTextField
import com.example.pregnapp.ui.components.ValidatedTextField
import com.example.pregnapp.util.NavigationRoutes

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavController
) {
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var name by remember{ mutableStateOf("") }
    var loading = false
    val snackbarHostState = SnackbarHostState()

    when(val state = viewModel.uiState.collectAsState().value){
        is AuthScreenState.Success -> { navController.navigate(NavigationRoutes.PROFILE)}
        is AuthScreenState.Error -> {
            LaunchedEffect(snackbarHostState){
                snackbarHostState.showSnackbar(
                    message = state.message
                )
            }
        }
        is AuthScreenState.Loading -> loading = true
        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        name = ValidatedTextField(
            hint = stringResource(id = R.string.name),
            isFieldValid = viewModel::isValidName,
            errorMessage = stringResource(id = R.string.incorrect_name),
            enabled = !loading
        )
        email = ValidatedTextField(
            hint = stringResource(id = R.string.email),
            isFieldValid = viewModel::isValidEmail,
            errorMessage = stringResource(id = R.string.incorrect_email),
            enabled = !loading
        )
        password = ValidatedPasswordTextField(
            hint = stringResource(id = R.string.password),
            isFieldValid = viewModel::isValidPassword,
            errorMessage = stringResource(id = R.string.incorrect_password),
            enabled = !loading
        )
        Button(
            modifier = Modifier.padding(5.dp),
            enabled = !loading && viewModel.isValidRegistration(name, email, password),
            onClick = {viewModel.register(name, email, password)}
        ) {
            Text(stringResource(id = R.string.register))
        }
        if(loading) LoadingDotsAnimation(
            circleSize = 15.dp,
            modifier = Modifier.padding(20.dp)
        )
        Text(
            text = stringResource(id = R.string.login),
            Modifier
                .padding(5.dp)
                .clickable { if (!loading) navController.navigate(NavigationRoutes.LOGIN) }
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SnackbarHost(
            hostState = snackbarHostState)
    }
}