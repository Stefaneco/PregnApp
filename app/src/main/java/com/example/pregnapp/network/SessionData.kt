package com.example.pregnapp.network

import kotlinx.serialization.Serializable

@Serializable
data class SessionData (
    var jwt : String,
    var refreshToken: String
        )