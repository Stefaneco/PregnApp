package com.example.pregnapp.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class SessionData (
    var jwt : String,
    var refreshToken: String
        )