package com.example.pregnapp.exceptions

class NoSessionException(
    override val message: String = "No Session Exception"
) : Exception() {
}