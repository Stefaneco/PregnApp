package com.example.pregnapp.exceptions

class BadRequestException(
    override val message: String = "Bad Request"
) : Exception() {
}