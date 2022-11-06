package com.example.pregnapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ValidatedPasswordTextField(
    hint: String,
    isFieldValid: (fieldValue: String) -> Boolean,
    errorMessage: String,
    width: Float = 0.7f,
    enabled: Boolean = true

): String{
    var wasFieldClicked by remember { mutableStateOf(false) }
    var wasFiledClickedOut by remember { mutableStateOf(false) }
    var fieldValue by remember { mutableStateOf("") }
    var displayError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column() {
        OutlinedTextField(
            modifier =
            Modifier
                .fillMaxWidth(width)
                .padding(5.dp)
                .onFocusEvent {
                    if (it.hasFocus) {
                        wasFieldClicked = true
                    }
                    if (!it.hasFocus && wasFieldClicked) {
                        wasFiledClickedOut = true
                        displayError = !isFieldValid(fieldValue)
                    }

                },
            value = fieldValue,
            label = { Text(text = hint) },
            onValueChange = {
                fieldValue = it
                displayError = !isFieldValid(fieldValue) && wasFiledClickedOut
            },
            singleLine = true,
            isError = displayError,
            enabled = enabled,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            }
        )
        if(displayError)
            Text(color = Color.Red,
                text = errorMessage)
    }
    return fieldValue
}