package com.example.pregnapp.exceptions

class InternalServerErrorException(
    override val message: String = "Internal Server Error"
) : Exception() {
}