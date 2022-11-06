package com.example.pregnapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ValidatedTextField(
    hint: String,
    isFieldValid: (fieldValue: String) -> Boolean,
    errorMessage: String,
    width: Float = 0.7f,
    enabled: Boolean = true,
    onValueChanged: (String) -> Unit = {},
    value: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) : String {
    var wasFieldClicked by remember { mutableStateOf(false) }
    var wasFiledClickedOut by remember { mutableStateOf(false) }
    var fieldValue by remember { mutableStateOf(value) }
    var displayError by remember { mutableStateOf(false) }

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
                onValueChanged(it)
            },
            singleLine = true,
            isError = displayError,
            enabled = enabled,
            keyboardOptions = keyboardOptions
        )
        if(displayError)
            Text(color = Color.Red,
                text = errorMessage)
    }
    return fieldValue
}